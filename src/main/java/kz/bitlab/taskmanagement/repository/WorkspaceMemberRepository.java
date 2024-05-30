package kz.bitlab.taskmanagement.repository;

import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.entity.key.WorkspaceMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, WorkspaceMemberKey> {

    List<WorkspaceMember> findByUser(User user);

}
