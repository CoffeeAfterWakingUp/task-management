package kz.bitlab.taskmanagement.rest.adapter;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.*;
import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.enums.BoardMemberRole;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.mapper.WorkspaceMapper;
import kz.bitlab.taskmanagement.service.BoardMemberService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static kz.bitlab.taskmanagement.util.SessionAttributes.CUR_USER;

@Component
@RequiredArgsConstructor
public class WorkspaceRestAdapter {

    private final WorkspaceMapper workspaceMapper;
    private final WorkspaceService workspaceService;
    private final UserService userService;
    private final WorkspaceMemberService workspaceMemberService;
    private final BoardMemberService boardMemberService;

    public ApiResponse<WorkspaceDTO> create(CreateWorkspaceDTO createWorkspaceDTO, HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute(CUR_USER);
        Workspace workspace = workspaceMapper.toEntity(createWorkspaceDTO);
        Workspace createdWorkspace = workspaceService.create(workspace, currentUser.getUsername());
        return ApiResponse.<WorkspaceDTO>builder()
                .body(workspaceMapper.toDTO(createdWorkspace))
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public ApiResponse<Boolean> addUser(AddUserToWorkspaceDTO addUserToWorkspaceDTO, Long id) {
        Workspace workspace = workspaceService.getById(id);
        String userRole = addUserToWorkspaceDTO.getUserRole();
        String username = addUserToWorkspaceDTO.getUsername();
        WorkspaceMemberRole workspaceMemberRole = WorkspaceMemberRole.valueOf(userRole);
        User user = userService.getByUsernameOrElseThrow(username);
        workspaceMemberService.create(workspace, user, workspaceMemberRole);
        workspace.getBoards().forEach(b -> {
            if (workspaceMemberRole.equals(WorkspaceMemberRole.WORKSPACE_ADMIN)) {
                boardMemberService.create(b, user, BoardMemberRole.BOARD_ADMIN);
            } else {
                if (b.getVisibility().equals(BoardVisibility.WORKSPACE)) {
                    boardMemberService.create(b, user, BoardMemberRole.BOARD_MEMBER);
                }
            }
        });
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public ApiResponse<Boolean> editWorkspaceName(Long id, EditWorkspaceNameDTO editWorkspaceNameDTO) {
        if (editWorkspaceNameDTO == null) throw new BadRequestException("request body is null");
        workspaceService.updateWorkspaceName(id, editWorkspaceNameDTO.getNewWorkspaceName());
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public ApiResponse<Boolean> deleteWorkspace(Long id) {
        workspaceService.deleteById(id);
        return ApiResponse.<Boolean>builder()
                .body(Boolean.TRUE)
                .status(HttpStatus.OK.value())
                .build();
    }
}
