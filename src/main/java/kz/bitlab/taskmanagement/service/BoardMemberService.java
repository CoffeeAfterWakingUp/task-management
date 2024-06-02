package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.BoardMember;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.key.BoardMemberKey;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;

import java.util.List;

public interface BoardMemberService {

    default BoardMemberKey id(Board board, User user) {
        if (board.getId() == null) throw new BadRequestException("Workspace id is null");
        if (user.getId() == null) throw new BadRequestException("User id is null");
        return new BoardMemberKey(board.getId(), user.getId());
    }

    BoardMember create(Board board, User user, BoardMemberRole boardMemberRole);

    BoardMember getById(Board board, User user);

    BoardMember getById(Long boardId, String username);

    List<BoardMember> getUserWorkspaceBoards(String username, Long workspaceId);
}
