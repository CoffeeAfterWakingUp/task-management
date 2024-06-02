package kz.bitlab.taskmanagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.adapter.UserAdapter;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.FavoriteBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserAdapter userAdapter;

    @PostMapping("/{username}/boards/favorites")
    public ResponseEntity<ApiResponse<Boolean>> addFavoritedBoard(@PathVariable String username,
                                                                  @RequestBody FavoriteBoardDTO favoriteBoardDTO,
                                                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApiResponse<Boolean> apiResponse = userAdapter.addFavoritedBoard(username, favoriteBoardDTO, session);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @DeleteMapping("/{username}/boards/favorites")
    public ResponseEntity<ApiResponse<Boolean>> removeFavoritedBoard(@PathVariable String username,
                                                                     @RequestBody FavoriteBoardDTO favoriteBoardDTO,
                                                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApiResponse<Boolean> apiResponse = userAdapter.removeFavoritedBoard(username, favoriteBoardDTO, session);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }
}
