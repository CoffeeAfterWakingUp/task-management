package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.Card;
import kz.bitlab.taskmanagement.entity.Task;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.TaskRepository;
import kz.bitlab.taskmanagement.service.CardService;
import kz.bitlab.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CardService cardService;

    @Override
    public Task create(Task task) {
        task.setCreatedTime(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found!"));
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTaskCard(Long id, Long cardId) {
        Task task = getById(id);
        Card card = cardService.getById(cardId);
        task.setCard(card);
        taskRepository.save(task);
    }
}
