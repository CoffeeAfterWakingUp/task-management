package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.CreateBoardDTO;
import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.BoardMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
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


    List<BoardDTO> toBoardDTOs(List<BoardMember> boardMembers);

    @Mapping(target = "id", source = "boardMember.board.id")
    @Mapping(target = "title", source = "boardMember.board.title")
    @Mapping(target = "description", source = "boardMember.board.description")
    @Mapping(target = "createdTime", source = "boardMember.board.createdTime")
    @Mapping(target = "cards", source = "boardMember.board.cards")
    @Mapping(target = "membersCount", expression = "java(boardMember.getBoard().getMembers() != null ? boardMember.getBoard().getMembers().size() : 0)")
    @Mapping(target = "cardsCount", expression = "java(boardMember.getBoard().getCards() != null ? boardMember.getBoard().getCards().size() : 0)")
    @Mapping(target = "boardVisibility", expression = "java(boardMember.getBoard().getVisibility().name())")
    @Mapping(target = "workspaceId", expression = "java(boardMember.getBoard().getWorkspace().getId())")
    @Mapping(target = "workspaceTitle", expression = "java(boardMember.getBoard().getWorkspace().getTitle())")
    BoardDTO toBoardDTO(BoardMember boardMember);

}
