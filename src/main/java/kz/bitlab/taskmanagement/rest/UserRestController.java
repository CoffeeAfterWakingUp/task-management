package kz.bitlab.taskmanagement.rest;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    @PostMapping("/{uId}/boards/favorites")
    public ResponseEntity<ApiResponse<Boolean>> addFavoritedBoards(@PathVariable Long uId, @RequestBody) {

    }
}
