package com.adapters.outbound.persistence.api.role.admin;

import com.adapters.outbound.persistence.api.role.RoleEntity;
import com.domain.model.api.role.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "adminEntity")
@Getter
@Setter
public class AdminDocument extends RoleEntity {

  private String farmId;

  @Builder
  public AdminDocument(String farmId, String name, String email, String id) {
    super(name, email, id);
    this.farmId = farmId;
  }

  public static AdminDocument fromDomain(Admin admin) {
    if (admin == null) {
      return null;
    }

    return AdminDocument.builder()
        .farmId(admin.getFarmId())
        .name(admin.getName())
        .email(admin.getEmail())
        .id(admin.getToken())
        .build();
  }

  public static Admin toDomain(AdminDocument adminDocument) {
    if (adminDocument == null) {
      return null;
    }
    return Admin.builder()
        .farmId(adminDocument.getFarmId())
        .name(adminDocument.getName())
        .token(adminDocument.getId())
        .email(adminDocument.getEmail())
        .token(adminDocument.getId())
        .build();
  }
}
