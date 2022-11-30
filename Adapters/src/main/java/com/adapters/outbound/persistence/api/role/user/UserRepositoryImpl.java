package com.adapters.outbound.persistence.api.role.user;

import com.domain.model.api.role.User;
import com.domain.ports.api.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserMongoRepository userMongoRepository;

  public UserRepositoryImpl(UserMongoRepository userMongoRepository) {
    this.userMongoRepository = userMongoRepository;
  }

  @Override
  public User getById(String id) {
    return userMongoRepository
        .findById(id)
        .map(UserDocument::toDomain)
        .orElseThrow(() -> new NotFoundException("User not found"));
  }

  @Override
  public List<User> findAllByAdminId(String adminId) {
    return userMongoRepository
        .findAllByAdminId(adminId)
        .stream()
        .map(UserDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void createUser(User user) {
    userMongoRepository.save(UserDocument.fromDomain(user));
  }

  @Override
  public void deleteUser(String userId) {
    userMongoRepository.deleteById(userId);
  }
}
