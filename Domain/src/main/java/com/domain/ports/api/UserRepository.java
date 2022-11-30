package com.domain.ports.api;

import com.domain.model.api.role.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

  User getById(String id);

  List<User> findAllByAdminId(String adminId);

  void createUser(User user);

  void deleteUser(String userId);
}
