package kz.bitlab.taskmanagement.repository;

import kz.bitlab.taskmanagement.entity.BoardMember;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.key.BoardMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, BoardMemberKey> {

    Optional<List<BoardMember>> findByUser(User user);

    @Query("select b from BoardMember b " +
            "where b.user.username=?1 and b.board.workspace.id=?2")
    Optional<List<BoardMember>> findByUserAndWorkspace(String username, Long workspaceId);
}
