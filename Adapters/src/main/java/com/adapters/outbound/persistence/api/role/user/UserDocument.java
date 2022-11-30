package com.adapters.outbound.persistence.api.role.user;


import com.adapters.outbound.persistence.api.role.RoleEntity;
import com.domain.model.api.role.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userEntity")
@Getter
@Setter
public class UserDocument extends RoleEntity {

  private String adminId;

  @Builder
  public UserDocument(String name, String email, String id, String adminId) {
    super(name, email, id);
    this.adminId = adminId;
  }

  public static UserDocument fromDomain(User user) {
    if (user == null) {
      return null;
    }

    UserDocumentBuilder builder = UserDocument.builder()
        .name(user.getName())
        .email(user.getEmail())
        .adminId(user.getAdminId());
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
        .name(userDocument.getName())
        .token(userDocument.getId())
        .email(userDocument.getEmail())
        .adminId(userDocument.getAdminId())
        .build();
  }
}