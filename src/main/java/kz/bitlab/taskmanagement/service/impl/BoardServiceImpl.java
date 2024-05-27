package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.repository.BoardRepository;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceService workspaceService;

    @Override
    public Board create(Board board, Long workspaceId) {
        if (board == null) {
            throw new BadRequestException("board is null");
        }
        if (workspaceId == null) {
            throw new BadRequestException("workspaceId is null");
        }

        String title = board.getTitle();
        BoardVisibility boardVisibility = board.getVisibility();
        if (title == null || title.isEmpty()) {
            throw new BadRequestException("title is empty");
        }
        if (boardVisibility == null) {
            throw new BadRequestException("board visibility is empty");
        }

        Workspace workspace = workspaceService.getById(workspaceId);
        board.setWorkspace(workspace);
        board.setCreatedTime(LocalDateTime.now());
        return boardRepository.save(board);
    }
}
