package kz.bitlab.taskmanagement.rest;


import kz.bitlab.taskmanagement.adapter.CardAdapter;
import kz.bitlab.taskmanagement.dto.AddCardDTO;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardRestController {

    private final CardAdapter cardAdapter;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> createCard(@RequestBody AddCardDTO addCardDTO) {
        ApiResponse<Boolean> apiResponse = cardAdapter.createCard(addCardDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

}
