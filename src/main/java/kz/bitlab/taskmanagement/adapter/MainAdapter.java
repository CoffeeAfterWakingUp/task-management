package kz.bitlab.taskmanagement.adapter;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.UserDTO;
import kz.bitlab.taskmanagement.dto.UserWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.mapper.WorkspaceMemberMapper;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.util.SessionAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MainAdapter {

    private final WorkspaceMemberService workspaceMemberService;
    private final WorkspaceMemberMapper workspaceMemberMapper;
    private final UserService userService;
    private final BoardMapper boardMapper;

    public String mainPage(HttpSession session, Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute(SessionAttribute.CUR_USER);
        Optional<User> userOpt = userService.getByUsername(currentUser.getUsername());
        Set<BoardDTO> favoritedBoards = boardMapper.toDTOs(userOpt.get().getFavoriteBoards());
        List<WorkspaceMember> workspaceMembers = workspaceMemberService.getByUser(currentUser.getUsername());
        List<UserWorkspaceDTO> userWorkspaceDTOS = workspaceMemberMapper.toDTOs(workspaceMembers);
        List<WorkspaceDTO> allWorkspaces = userWorkspaceDTOS.stream().map(UserWorkspaceDTO::getWorkspaceDTO).collect(Collectors.toList());
        model.addAttribute("userWorkspaces", userWorkspaceDTOS);
        session.setAttribute(SessionAttribute.ALL_WORKSPACES, allWorkspaces);
        session.setAttribute(SessionAttribute.FAVORITED_BOARDS, favoritedBoards);
        return "main";
    }
}
