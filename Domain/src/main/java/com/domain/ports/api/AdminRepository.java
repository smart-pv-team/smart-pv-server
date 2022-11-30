package com.domain.ports.api;

import com.domain.model.api.role.Admin;
import com.domain.model.api.role.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository {

  Admin getById(String id);

  List<User> getPupils(String id);

  void createAdmin(Admin admin);

  void deleteAdmin(String adminId);
}