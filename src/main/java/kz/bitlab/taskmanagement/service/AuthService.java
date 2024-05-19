package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.RegisterDTO;

public interface AuthService {

    ApiResponse<String> register(RegisterDTO registerDTO);
}
