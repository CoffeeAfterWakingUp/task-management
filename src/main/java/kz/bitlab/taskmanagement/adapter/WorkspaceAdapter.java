package kz.bitlab.taskmanagement.adapter;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.*;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.mapper.WorkspaceMapper;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import kz.bitlab.taskmanagement.util.SessionAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.Set;

import static kz.bitlab.taskmanagement.util.SessionAttribute.CUR_USER;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter {

    private final WorkspaceService workspaceService;
    private final WorkspaceMapper workspaceMapper;
    private final WorkspaceMemberService workspaceMemberService;
    private final UserService userService;

    public ApiResponse<WorkspaceDTO> create(CreateWorkspaceDTO createWorkspaceDTO, HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute(CUR_USER);
        Workspace workspace = workspaceMapper.toEntity(createWorkspaceDTO);
        Workspace createdWorkspace = workspaceService.create(workspace, currentUser.getUsername());
        return ApiResponse.<WorkspaceDTO>builder()
                .body(workspaceMapper.toDTO(createdWorkspace))
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public String getWorkspaceBoards(Long id, Model model, HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute(CUR_USER);
        Optional<User> userOpt = userService.getByUsername(currentUser.getUsername());
        Workspace workspace = workspaceService.getById(id);
        WorkspaceMember workspaceMember = workspaceMemberService.getById(workspace, userOpt.get());
        WorkspaceDTO workspaceDTO = workspaceMapper.toDTO(workspace);
        Set<String> boardVisibilities = BoardVisibility.toSet();
        Set<String> workspaceMemberRoles = WorkspaceMemberRole.toSet();
        model.addAttribute("workspace", workspaceDTO);
        model.addAttribute("boardVisibilities", boardVisibilities);
        session.setAttribute(SessionAttribute.CUR_WORKSPACE, workspaceDTO);
        session.setAttribute(SessionAttribute.WORKSPACE_MEMBER_ROLES, workspaceMemberRoles);
        session.setAttribute(SessionAttribute.CUR_USER_WORKSPACE_ROLE, workspaceMember.getMemberRole());
        return "workspace";
    }

    public ApiResponse<Boolean> addUser(AddUserToWorkspaceDTO addUserToWorkspaceDTO, Long id) {
        Workspace workspace = workspaceService.getById(id);
        String userRole = addUserToWorkspaceDTO.getUserRole();
        String username = addUserToWorkspaceDTO.getUsername();
        Optional<User> userOpt = userService.getByUsername(username);
        workspaceMemberService.create(workspace, userOpt.get(), WorkspaceMemberRole.valueOf(userRole));
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .build();
    }
}
