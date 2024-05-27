package kz.bitlab.taskmanagement.adapter;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.CreateBoardDTO;
import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.util.LongParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardAdapter {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    public ApiResponse<BoardDTO> createBoard(CreateBoardDTO createBoardDTO) {
        Board board = boardMapper.toEntity(createBoardDTO);
        Long id = LongParser.parse(createBoardDTO.getWorkspaceId());
        Board createdBoard = boardService.create(board, id);
        return ApiResponse.<BoardDTO>builder()
                .status(HttpStatus.CREATED.value())
                .body(boardMapper.toDTO(createdBoard))
                .build();
    }
}
