package kz.bitlab.taskmanagement.repository;

import kz.bitlab.taskmanagement.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
