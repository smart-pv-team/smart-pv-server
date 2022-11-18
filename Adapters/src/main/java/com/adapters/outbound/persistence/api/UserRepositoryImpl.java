package com.adapters.outbound.persistence.api;

import com.domain.model.api.User;
import com.domain.ports.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserMongoRepository userMongoRepository;

  @Override
  public Optional<User> getById(String id) {
    return userMongoRepository.findById(id).map(UserDocument::toDomain);
  }

  @Override
  public List<User> getByFarmId(List<String> farmIds) {
    return userMongoRepository.findByFarmIdIn(farmIds).stream().map(UserDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void updateUser(User user) {
    userMongoRepository.save(UserDocument.fromDomain(user));
  }
}
