package com.adapters.inbound.http.api;

import com.adapters.inbound.http.Routing;
import com.domain.model.api.role.Admin;
import com.domain.model.api.role.User;
import com.domain.ports.api.AdminRepository;
import com.domain.ports.api.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoleController {

  private final UserRepository userRepository;
  private final AdminRepository adminRepository;

  @Autowired
  public RoleController(UserRepository userRepository, AdminRepository adminRepository) {
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
  }


  @GetMapping(Routing.Api.User.Token.TokenId.PATH)
  ResponseEntity<User> getUserByToken(@PathVariable(Routing.TOKEN_VARIABLE) String token) {
    return ResponseEntity.ok(userRepository.getById(token));
  }

  @PutMapping(Routing.Api.User.PATH)
  void createUser(@RequestBody User user) {
    userRepository.createUser(user);
  }

  @DeleteMapping(Routing.Api.User.Token.TokenId.PATH)
  void deleteUser(@PathVariable(Routing.TOKEN_VARIABLE) String token) {
    userRepository.deleteUser(token);
  }

  @PutMapping(Routing.Api.Admin.PATH)
  void createAdmin(@RequestBody Admin admin) {
    adminRepository.createAdmin(admin);
  }

  @GetMapping(Routing.Api.Admin.Token.TokenId.PATH)
  ResponseEntity<Admin> getAdmin(@PathVariable(Routing.TOKEN_VARIABLE) String token) {
    return ResponseEntity.ok(adminRepository.getById(token));
  }

  @GetMapping(Routing.Api.Admin.Token.TokenId.Pupils.PATH)
  List<User> getAdminPupils(@PathVariable(Routing.TOKEN_VARIABLE) String token) {
    return adminRepository.getPupils(token);
  }
}
