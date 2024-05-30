package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.CreateBoardDTO;
import kz.bitlab.taskmanagement.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = CardMapper.class)
public interface BoardMapper {

    Set<BoardDTO> toDTOs(Set<Board> boards);

    @Mapping(target = "membersCount", expression = "java(board.getMembers() != null ? board.getMembers().size() : 0)")
    @Mapping(target = "cardsCount", expression = "java(board.getCards() != null ? board.getCards().size() : 0)")
    @Mapping(target = "boardVisibility", expression = "java(board.getVisibility().name())")
    @Mapping(target = "workspaceId", expression = "java(board.getWorkspace().getId())")
    @Mapping(target = "workspaceTitle", expression = "java(board.getWorkspace().getTitle())")
    BoardDTO toDTO(Board board);

    @Mapping(target = "visibility", expression = "java(kz.bitlab.taskmanagement.enums.BoardVisibility.valueOf(createBoardDTO.getBoardVisibility()))")
    Board toEntity(CreateBoardDTO createBoardDTO);

}
