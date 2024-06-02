package kz.bitlab.taskmanagement.rest;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.CreateTaskDTO;
import kz.bitlab.taskmanagement.dto.TaskDTO;
import kz.bitlab.taskmanagement.dto.UpdateTaskCardDTO;
import kz.bitlab.taskmanagement.rest.adapter.TaskRestAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskRestAdapter taskAdapter;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        ApiResponse<Boolean> apiResponse = taskAdapter.createTask(createTaskDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TaskDTO>> getById(@PathVariable Long id) {
        ApiResponse<TaskDTO> apiResponse = taskAdapter.getById(id);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> deleteById(@PathVariable Long id) {
        ApiResponse<Boolean> apiResponse = taskAdapter.deleteById(id);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @PatchMapping("/{id}/card")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> updateTaskCard(@PathVariable Long id, @RequestBody UpdateTaskCardDTO updateTaskCardDTO) {
        ApiResponse<Boolean> apiResponse = taskAdapter.updateTaskCard(id, updateTaskCardDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }
}
