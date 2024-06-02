package kz.bitlab.taskmanagement.adapter;

import kz.bitlab.taskmanagement.dto.AddCardDTO;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.entity.Card;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.mapper.CardMapper;
import kz.bitlab.taskmanagement.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardAdapter {

    private final CardService cardService;
    private final CardMapper cardMapper;

    public ApiResponse<Boolean> createCard(AddCardDTO addCardDTO) {
        if (addCardDTO == null) throw new BadRequestException("AddCardDTO is null");
        Card card = cardMapper.toEntity(addCardDTO);
        cardService.create(card, addCardDTO.getBoardId());
        return ApiResponse.<Boolean>builder().body(Boolean.TRUE).status(HttpStatus.CREATED.value()).build();
    }
}
