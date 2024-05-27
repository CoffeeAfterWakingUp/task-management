package kz.bitlab.taskmanagement.rest;

import kz.bitlab.taskmanagement.adapter.BoardAdapter;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.CreateBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardAdapter boardAdapter;

    @PostMapping
    public ResponseEntity<ApiResponse<BoardDTO>> create(@RequestBody CreateBoardDTO createBoardDTO) {
        ApiResponse<BoardDTO> apiResponse = boardAdapter.createBoard(createBoardDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }
}