package com.domain.ports;

import com.domain.model.api.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

  Optional<User> getById(String id);

  List<User> getByFarmId(List<String> farmIds);

  void updateUser(User user);
}
