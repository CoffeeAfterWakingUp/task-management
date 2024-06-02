package kz.bitlab.taskmanagement.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.RegisterDTO;
import kz.bitlab.taskmanagement.dto.UserDTO;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.mapper.UserMapper;
import kz.bitlab.taskmanagement.service.AuthService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.PropertiesReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import static kz.bitlab.taskmanagement.util.SessionAttributes.CUR_USER;

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

        if (isParameterEmpty(fullName)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.fullNameEmpty"))
                    .build();
        }
        if (isParameterEmpty(email)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.emailEmpty"))
                    .build();
        }
        if (isParameterEmpty(username)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.usernameEmpty"))
                    .build();
        }
        if (isParameterEmpty(password)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.passwordEmpty"))
                    .build();
        }
        if (isParameterEmpty(rePassword)) {
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
        User user = userService.getByUsername(username);
        if (user != null) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMsg(PROPERTIES.getProperty("error.usernameExists"))
                    .build();
        }

        User newUser = userMapper.toUser(registerDTO);
        newUser.setRegisterTime(LocalDateTime.now());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userService.create(newUser);

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .body(PROPERTIES.getProperty("message.userCreated"))
                .build();
    }

    @Override
    public String loginError(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                return PROPERTIES.getProperty("error.checkCredentials");
            }
        }
        return "";
    }

    @Override
    public String loginSuccess(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        if (session != null) {
            UserDetails authUser = (UserDetails) authentication.getPrincipal();
            String username = authUser.getUsername();
            User user = userService.getByUsernameOrElseThrow(username);
            if (user != null) {
                UserDTO userDTO = userMapper.toDTO(user);
                session.setAttribute(CUR_USER, userDTO);
                return "ok";
            }
        }
        return "error";
    }

    private boolean isParameterEmpty(String parameter) {
        return parameter == null || parameter.isEmpty();
    }
}
