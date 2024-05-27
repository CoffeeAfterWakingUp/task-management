package kz.bitlab.taskmanagement.exception.handler;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handle(BadRequestException ex) {
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorMsg(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handle(NotFoundException ex) {
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .errorMsg(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }
}
