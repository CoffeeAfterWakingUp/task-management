package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.CreateBoardDTO;
import kz.bitlab.taskmanagement.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    @Mapping(target = "membersCount", expression = "java(board.getMembers() != null ? board.getMembers().size() : 0)")
    @Mapping(target = "cardsCount", expression = "java(board.getCards() != null ? board.getCards().size() : 0)")
    BoardDTO toDTO(Board board);

    @Mapping(target = "visibility", expression = "java(kz.bitlab.taskmanagement.enums.BoardVisibility.valueOf(createBoardDTO.getBoardVisibility()))")
    Board toEntity(CreateBoardDTO createBoardDTO);

}
