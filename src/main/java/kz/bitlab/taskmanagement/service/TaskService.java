package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.Task;

public interface TaskService {

    Task create(Task task);

    Task getById(Long id);

    void deleteById(Long id);

    void updateTaskCard(Long id, Long cardId);
}
