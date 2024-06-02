package kz.bitlab.taskmanagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.*;
import kz.bitlab.taskmanagement.rest.adapter.BoardRestAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardRestAdapter boardAdapter;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BoardDTO>> create(@RequestBody CreateBoardDTO createBoardDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApiResponse<BoardDTO> apiResponse = boardAdapter.createBoard(createBoardDTO, session);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @PostMapping("/{id}/members")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> addMember(@PathVariable Long id, @RequestBody AddBoardMemberDTO addBoardMemberDTO) {
        ApiResponse<Boolean> apiResponse = boardAdapter.addMember(id, addBoardMemberDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @PutMapping("/{id}/track-cards-orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> trackCardOrders(@RequestBody TrackCardOrderDTO trackCardOrderDTO) {
        ApiResponse<Boolean> apiResponse = boardAdapter.trackCardOrders(trackCardOrderDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }
}
