package kz.bitlab.taskmanagement.rest.adapter;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.*;
import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.Card;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.service.BoardMemberService;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.LongParser;
import kz.bitlab.taskmanagement.util.SessionAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class BoardRestAdapter {

    @Lazy
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final BoardMemberService boardMemberService;
    private final UserService userService;

    public ApiResponse<BoardDTO> createBoard(CreateBoardDTO createBoardDTO, HttpSession session) {
        UserDTO curUser = (UserDTO) session.getAttribute(SessionAttributes.CUR_USER);
        Board board = boardMapper.toEntity(createBoardDTO);
        Long id = LongParser.parse(createBoardDTO.getWorkspaceId());


        Board createdBoard = boardService.create(board, id);
        createDefaultCards(board);
        addCurUserToBoard(curUser, createdBoard);

        return ApiResponse.<BoardDTO>builder()
                .status(HttpStatus.CREATED.value())
                .body(boardMapper.toDTO(createdBoard))
                .build();
    }

    public ApiResponse<Boolean> trackCardOrders(TrackCardOrderDTO trackCardOrderDTO) {
        if (trackCardOrderDTO == null) throw new BadRequestException("TrackCardOrderDTO is null");
        boardService.updateCardOrders(trackCardOrderDTO.getCardIds(), trackCardOrderDTO.getBoardId());
        return ApiResponse.<Boolean>builder().body(Boolean.TRUE).status(HttpStatus.OK.value()).build();
    }

    public ApiResponse<Boolean> addMember(Long id, AddBoardMemberDTO addBoardMemberDTO) {
        String member = addBoardMemberDTO.getMember();
        String memberRole = addBoardMemberDTO.getMemberRole();
        Board board = boardService.getById(id);
        User user = userService.getByUsername(member);
        boardMemberService.create(board, user, BoardMemberRole.valueOf(memberRole));
        return ApiResponse.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .body(Boolean.TRUE)
                .build();
    }

    private void addCurUserToBoard(UserDTO curUser, Board board) {
        User user = userService.getByUsernameOrElseThrow(curUser.getUsername());
        boardMemberService.create(board, user, BoardMemberRole.BOARD_ADMIN);
    }

    private void createDefaultCards(Board board) {
        Card firstCard = new Card(1, "Нужно сделать");
        Card secondCard = new Card(2, "В процессе");
        Card thirdCard = new Card(3, "Готово");

        board.addCard(firstCard);
        board.addCard(secondCard);
        board.addCard(thirdCard);

    }
}
