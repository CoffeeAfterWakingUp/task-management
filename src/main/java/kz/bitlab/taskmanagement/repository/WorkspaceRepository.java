package kz.bitlab.taskmanagement.repository;

import kz.bitlab.taskmanagement.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
