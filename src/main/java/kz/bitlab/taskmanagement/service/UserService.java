package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.User;

import java.util.Optional;

public interface UserService {

    User getByUsernameOrElseThrow(String username);

    User getByUsername(String username);

    Optional<User> getByEmail(String email);

    Optional<User> create(User user);

    void addFavoriteBoard(String username, Long boardId);

    void removeFavoritedBoard(String username, Long boardId);

    User getById(Long id);

    void delete(Long id);


}
