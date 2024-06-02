package kz.bitlab.taskmanagement.rest.adapter;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.CreateTaskDTO;
import kz.bitlab.taskmanagement.dto.TaskDTO;
import kz.bitlab.taskmanagement.dto.UpdateTaskCardDTO;
import kz.bitlab.taskmanagement.entity.Task;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.mapper.TaskMapper;
import kz.bitlab.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRestAdapter {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public ApiResponse<Boolean> createTask(CreateTaskDTO createTaskDTO) {
        if (createTaskDTO == null) throw new BadRequestException("request body is null");
        Task task = taskMapper.toEntity(createTaskDTO);
        taskService.create(task);
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public ApiResponse<TaskDTO> getById(Long id) {
        Task task = taskService.getById(id);
        TaskDTO taskDTO = taskMapper.taskDTO(task);
        return ApiResponse.<TaskDTO>builder()
                .body(taskDTO)
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public ApiResponse<Boolean> deleteById(Long id) {
        taskService.deleteById(id);
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public ApiResponse<Boolean> updateTaskCard(Long id, UpdateTaskCardDTO updateTaskCardDTO) {
        Long cardId = updateTaskCardDTO.getCardId();
        taskService.updateTaskCard(id, cardId);
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .build();
    }


}
