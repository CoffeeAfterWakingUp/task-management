package kz.bitlab.taskmanagement.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.RegisterDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {

    ApiResponse<String> register(RegisterDTO registerDTO);

    String loginError(HttpServletRequest request);

    String loginSuccess(HttpServletRequest request, Authentication authentication);
}
