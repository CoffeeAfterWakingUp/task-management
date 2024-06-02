package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.AddCardDTO;
import kz.bitlab.taskmanagement.dto.CardDTO;
import kz.bitlab.taskmanagement.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface CardMapper {

    CardDTO toDTO(Card card);

    Card toEntity(AddCardDTO addCardDTO);
}
