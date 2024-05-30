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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private final BoardMemberRepository boardMemberRepository;

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
}
