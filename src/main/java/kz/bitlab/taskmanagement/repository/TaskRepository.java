package kz.bitlab.taskmanagement.repository;

import kz.bitlab.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
