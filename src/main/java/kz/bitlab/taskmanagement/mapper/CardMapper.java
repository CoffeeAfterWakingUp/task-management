package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.CardDTO;
import kz.bitlab.taskmanagement.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDTO toDTO(Card card);
}
