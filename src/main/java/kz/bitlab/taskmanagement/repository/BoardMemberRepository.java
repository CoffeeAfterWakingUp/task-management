package kz.bitlab.taskmanagement.repository;

import kz.bitlab.taskmanagement.entity.BoardMember;
import kz.bitlab.taskmanagement.entity.key.BoardMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMemberRepository extends JpaRepository<BoardMember, BoardMemberKey> {
}
