package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.RegisterDTO;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.mapper.UserMapper;
import kz.bitlab.taskmanagement.service.AuthService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.PropertiesReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Properties PROPERTIES = PropertiesReader.getRuProperties();

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<String> register(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        String fullName = registerDTO.getFullName();
        String rePassword = registerDTO.getRePassword();
        String username = registerDTO.getUsername();

        if (fullName == null || fullName.isEmpty()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.fullNameEmpty"))
                    .build();
        }
        if (email == null || email.isEmpty()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.emailEmpty"))
                    .build();
        }
        if (username == null || username.isEmpty()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.usernameEmpty"))
                    .build();
        }
        if (password == null || password.isEmpty()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.passwordEmpty"))
                    .build();
        }
        if (rePassword == null || rePassword.isEmpty()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.rePasswordEmpty"))
                    .build();
        }
        if (!password.equals(rePassword)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.passwordsNotEqual"))
                    .build();
        }

        Optional<User> userByEmail = userService.getByEmail(email);
        if (userByEmail.isPresent()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.emailExists"))
                    .build();
        }
        Optional<User> userByUsername = userService.getByUsername(username);
        if (userByUsername.isPresent()) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.usernameExists"))
                    .build();
        }

        User user = userMapper.toUser(registerDTO);
        user.setRegisterTime(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .body(PROPERTIES.getProperty("message.userCreated"))
                .build();
    }
}
