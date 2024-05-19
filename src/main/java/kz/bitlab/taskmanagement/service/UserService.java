package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);

    Optional<User> create(User user);


}
