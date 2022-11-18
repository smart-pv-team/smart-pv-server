package com.adapters.inbound.http.api;

import com.adapters.inbound.http.Routing;
import com.domain.model.api.User;
import com.domain.ports.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping(Routing.Api.User.Token.TokenId.PATH)
  ResponseEntity<User> getUserByToken(@PathVariable(Routing.TOKEN_VARIABLE) String token) {
    return ResponseEntity.of(userRepository.getById(token));
  }

  @GetMapping(Routing.Api.User.Farm.FarmId.PATH)
  List<User> getUsersByFarmIds(@PathVariable(Routing.FARM_ID_VARIABLE) List<String> farmId) {
    return userRepository.getByFarmId(farmId);
  }

  @PutMapping(Routing.Api.User.PATH)
  void createUser(@RequestBody User user) {
    userRepository.updateUser(user);
  }
}
