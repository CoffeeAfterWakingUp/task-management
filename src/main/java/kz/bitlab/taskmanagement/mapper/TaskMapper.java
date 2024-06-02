package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.CreateTaskDTO;
import kz.bitlab.taskmanagement.dto.TaskDTO;
import kz.bitlab.taskmanagement.entity.Task;
import kz.bitlab.taskmanagement.service.CardService;
import kz.bitlab.taskmanagement.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    @Autowired
    protected UserService userService;
    @Autowired
    protected CardService cardService;

    @Mapping(target = "title", source = "createTaskDTO.taskTitle")
    @Mapping(target = "description", source = "createTaskDTO.taskDescription")
    @Mapping(target = "worker", expression = "java(userService.getByUsernameOrElseThrow(createTaskDTO.getWorker()))")
    @Mapping(target = "author", expression = "java(userService.getByUsernameOrElseThrow(createTaskDTO.getAuthor()))")
    @Mapping(target = "card", expression = "java(cardService.getById(createTaskDTO.getCardId()))")
    public abstract Task toEntity(CreateTaskDTO createTaskDTO);

    public abstract List<TaskDTO> taskDTOs(List<Task> tasks);

    @Mapping(target = "worker", expression = "java(task.getWorker().getFullName())")
    @Mapping(target = "author", expression = "java(task.getAuthor().getFullName())")
    public abstract TaskDTO taskDTO(Task task);

}
