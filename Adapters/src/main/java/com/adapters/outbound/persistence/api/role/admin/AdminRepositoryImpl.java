package com.adapters.outbound.persistence.api.role.admin;

import com.domain.model.api.role.Admin;
import com.domain.model.api.role.User;
import com.domain.ports.api.AdminRepository;
import com.domain.ports.api.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

@Repository
@AllArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

  private final AdminMongoRepository adminMongoRepository;
  private final UserRepository userRepository;


  @Override
  public Admin getById(String id) {
    return adminMongoRepository
        .findById(id)
        .map(AdminDocument::toDomain)
        .orElseThrow(() -> new NotFoundException("Admin not found"));
  }

  @Override
  public List<User> getPupils(String id) {
    return userRepository.findAllByAdminId(id);
  }

  @Override
  public void createAdmin(Admin admin) {
    adminMongoRepository.save(AdminDocument.fromDomain(admin));
  }

  @Override
  public void deleteAdmin(String adminId) {
    List<String> pupilsIds = getPupils(adminId).stream().map(User::getToken).toList();
    pupilsIds.forEach(userRepository::deleteUser);
    adminMongoRepository.deleteById(adminId);
  }
}
