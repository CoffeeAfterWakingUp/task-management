package kz.bitlab.taskmanagement.controller.adapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.UserDTO;
import kz.bitlab.taskmanagement.entity.Board;
import kz.bitlab.taskmanagement.entity.BoardMember;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import kz.bitlab.taskmanagement.enums.TaskPriority;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.service.BoardMemberService;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.ModelAttributes;
import kz.bitlab.taskmanagement.util.SessionAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BoardAdapter {


    private final UserService userService;
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final WorkspaceAdapter workspaceAdapter;
    private final BoardMemberService boardMemberService;

    public String boardPage(Long wId, Long bId, Model model, HttpSession session) {
        UserDTO curUser = (UserDTO) session.getAttribute(SessionAttributes.CUR_USER);
        User user = userService.getByUsernameOrElseThrow(curUser.getUsername());
        Board board = boardService.getById(bId);
        BoardDTO boardDTO = boardMapper.toDTO(board);
        BoardMember boardMember = boardMemberService.getById(board, user);
        Set<BoardMember> boardMembers = board.getMembers();
        List<String> members = boardMembers.stream().map(m -> m.getUser().getUsername()).collect(Collectors.toList());

        updateRecentBoards(session, boardDTO);
        model.addAttribute(ModelAttributes.BOARD, boardDTO);
        model.addAttribute(ModelAttributes.BOARD_FAVORITED, user.getFavoriteBoards().contains(board));
        model.addAttribute(ModelAttributes.BOARD_MEMBER_ROLES, BoardMemberRole.toSet());
        model.addAttribute(ModelAttributes.CUR_USER_BOARD_ROLE, boardMember.getBoardMemberRole().name());
        model.addAttribute(ModelAttributes.TASK_PRIORITES, TaskPriority.toSet());
        model.addAttribute(ModelAttributes.BOARD_MEMBERS, members);
        return "board";
    }

    private void updateRecentBoards(HttpSession session, BoardDTO boardDTO) {
        Object sessionAttribute = session.getAttribute(SessionAttributes.RECENT_BOARDS);
        LinkedList<BoardDTO> recentBoards;
        if (sessionAttribute == null) {
            recentBoards = new LinkedList<>();
        } else {
            recentBoards = (LinkedList<BoardDTO>) sessionAttribute;
            recentBoards.remove(boardDTO);
        }
        recentBoards.addFirst(boardDTO);
        session.setAttribute(SessionAttributes.RECENT_BOARDS, recentBoards);
    }

    public void populateModel(Long id, HttpServletRequest request, Model model) {
        workspaceAdapter.populateModel(id, request, model);
    }
}
