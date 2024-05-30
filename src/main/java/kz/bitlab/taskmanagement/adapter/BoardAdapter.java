package kz.bitlab.taskmanagement.adapter;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.*;
import kz.bitlab.taskmanagement.entity.*;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.mapper.WorkspaceMapper;
import kz.bitlab.taskmanagement.service.BoardMemberService;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.LongParser;
import kz.bitlab.taskmanagement.util.ModelAttribute;
import kz.bitlab.taskmanagement.util.RecentSet;
import kz.bitlab.taskmanagement.util.SessionAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ ={@Lazy})
public class BoardAdapter {

    @Lazy
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final BoardMemberService boardMemberService;
    private final UserService userService;
    private final WorkspaceMapper workspaceMapper;

    public ApiResponse<BoardDTO> createBoard(CreateBoardDTO createBoardDTO, HttpSession session) {
        UserDTO curUser = (UserDTO) session.getAttribute(SessionAttribute.CUR_USER);
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

    public String boardPage(Long wId, Long bId, Model model, HttpSession session) {
        UserDTO curUser = (UserDTO) session.getAttribute(SessionAttribute.CUR_USER);
        Optional<User> userOpt = userService.getByUsername(curUser.getUsername());
        Board board = boardService.getById(bId);
        Workspace workspace = board.getWorkspace();
        WorkspaceDTO workspaceDTO = workspaceMapper.toDTO(workspace);
        BoardDTO boardDTO = boardMapper.toDTO(board);
        BoardMember boardMember = boardMemberService.getById(board, userOpt.get());
        updateRecentBoards(session, boardDTO);
        model.addAttribute("board", boardDTO);
        model.addAttribute(ModelAttribute.BOARD_FAVORITED, userOpt.get().getFavoriteBoards().contains(board));
        session.setAttribute(SessionAttribute.CUR_WORKSPACE, workspaceDTO);
        session.setAttribute(SessionAttribute.CUR_USER_BOARD_ROLE, boardMember.getBoardMemberRole().name());
        return "board";
    }

    private void addCurUserToBoard(UserDTO curUser, Board board) {
        Optional<User> userOpt = userService.getByUsername(curUser.getUsername());
        boardMemberService.create(board, userOpt.get(), BoardMemberRole.BOARD_ADMIN);
    }

    private void updateRecentBoards(HttpSession session, BoardDTO boardDTO) {
        Object sessionAttribute = session.getAttribute(SessionAttribute.RECENT_BOARDS);
        if (sessionAttribute == null) {
            sessionAttribute = new RecentSet<>();
        }
        RecentSet<BoardDTO> recentBoards = (RecentSet<BoardDTO>) sessionAttribute;
        recentBoards.add(boardDTO);
        session.setAttribute(SessionAttribute.RECENT_BOARDS, recentBoards);
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
