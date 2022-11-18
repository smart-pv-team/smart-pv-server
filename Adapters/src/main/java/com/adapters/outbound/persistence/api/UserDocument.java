package com.adapters.outbound.persistence.api;


import com.domain.model.api.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "userEntity")
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument {

  private String farmId;
  private String name;
  @Id
  private String id;

  @PersistenceCreator
  public UserDocument(String farmId, String name) {
    this.farmId = farmId;
    this.name = name;
  }

  public static UserDocument fromDomain(User user) {
    if (user == null) {
      return null;
    }

    UserDocumentBuilder builder = UserDocument.builder()
        .farmId(user.getFarmId())
        .name(user.getName());
    if (user.getToken() != null) {
      builder.id(user.getToken());
    }
    return builder.build();
  }

  public static User toDomain(UserDocument userDocument) {
    if (userDocument == null) {
      return null;
    }
    return User.builder()
        .farmId(userDocument.getFarmId())
        .name(userDocument.getName())
        .token(userDocument.getId())
        .build();
  }
}