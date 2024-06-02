package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.BoardMember;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.key.BoardMemberKey;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.BoardMemberRepository;
import kz.bitlab.taskmanagement.service.BoardMemberService;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private final BoardMemberRepository boardMemberRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Override
    public BoardMember create(Board board, User user, BoardMemberRole boardMemberRole) {
        if (board == null) {
            throw new BadRequestException("Board is null");
        }
        if (user == null) {
            throw new BadRequestException("User is null");
        }
        if (boardMemberRole == null) {
            throw new BadRequestException("BoardMember is null");
        }
        BoardMember boardMember = BoardMember.builder()
                .id(id(board, user))
                .board(board)
                .user(user)
                .boardMemberRole(boardMemberRole)
                .build();
        return boardMemberRepository.save(boardMember);
    }

    @Override
    public BoardMember getById(Board board, User user) {
        if (board == null) {
            throw new BadRequestException("Board is null");
        }
        if (user == null) {
            throw new BadRequestException("User is null");
        }
        BoardMemberKey id = id(board, user);
        return boardMemberRepository.findById(id).orElseThrow(() -> new NotFoundException("Board Member not found"));
    }

    @Override
    public BoardMember getById(Long boardId, String username) {
        if (boardId == null) throw new BadRequestException("boardId is null");
        if (username == null) throw new BadRequestException("username is null");
        User user = userService.getByUsernameOrElseThrow(username);
        Board board = boardService.getById(boardId);
        return getById(board, user);
    }

    @Override
    public List<BoardMember> getUserWorkspaceBoards(String username, Long workspaceId) {
        if (username == null) throw new BadRequestException("username is null");
        if (workspaceId == null) throw new BadRequestException("workspaceId is null");
        return boardMemberRepository.findByUserAndWorkspace(username, workspaceId).orElse(Collections.emptyList());
    }
}
