package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.UserRepository;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BoardService boardService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null) {
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                );
            }
        }
        return null;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        if (username == null) throw new BadRequestException("Username is null");
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) throw new NotFoundException("User is not found!");
        return userOpt;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> create(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public User getById(Long id) {
        if (id == null) throw new BadRequestException("id is null");
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElseThrow(() -> new NotFoundException("User is not found!"));
    }

    @Override
    public void addFavoriteBoard(String username, Long boardId) {
        if (username == null) throw new BadRequestException("username is null");
        if (boardId == null) throw new BadRequestException("board id is null");

        User user = getByUsername(username).get();
        Board board = boardService.getById(boardId);

        user.addFavoriteBoard(board);
        userRepository.save(user);
    }

    @Override
    public void removeFavoritedBoard(String username, Long boardId) {
        if (username == null) throw new BadRequestException("username is null");
        if (boardId == null) throw new BadRequestException("board id is null");

        User user = getByUsername(username).get();
        Board board = boardService.getById(boardId);

        user.removeFavoriteBoard(board);
        userRepository.save(user);
    }
}
