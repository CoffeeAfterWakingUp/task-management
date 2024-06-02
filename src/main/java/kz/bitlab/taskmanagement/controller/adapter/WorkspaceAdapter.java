package kz.bitlab.taskmanagement.controller.adapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.BoardDTO;
import kz.bitlab.taskmanagement.dto.UserDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceMemberDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceMembersDTO;
import kz.bitlab.taskmanagement.entity.BoardMember;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.mapper.BoardMapper;
import kz.bitlab.taskmanagement.mapper.WorkspaceMemberMapper;
import kz.bitlab.taskmanagement.service.BoardMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import kz.bitlab.taskmanagement.util.ModelAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kz.bitlab.taskmanagement.util.ModelAttributes.MEMBERS;
import static kz.bitlab.taskmanagement.util.SessionAttributes.CUR_USER;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter {

    private final WorkspaceService workspaceService;
    private final WorkspaceMemberMapper workspaceMemberMapper;
    private final WorkspaceMemberService workspaceMemberService;
    private final BoardMemberService boardMemberService;
    private final BoardMapper boardMapper;

    public String getWorkspaceBoards(Long workspaceId, Model model, HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute(CUR_USER);
        List<BoardMember> boardMembers = boardMemberService.getUserWorkspaceBoards(currentUser.getUsername(), workspaceId);
        List<BoardDTO> userBoards = boardMapper.toBoardDTOs(boardMembers);
        Set<String> boardVisibilities = BoardVisibility.toSet();
        model.addAttribute(ModelAttributes.CUR_USER_BOARDS, userBoards);
        model.addAttribute(ModelAttributes.BOARD_VISIBILITIES, boardVisibilities);
        return "workspace";
    }

    public String getWorkspaceMembers(Long id, Model model, HttpSession session) {
        Workspace workspace = workspaceService.getById(id);
        Set<WorkspaceMember> workspaceMembers = workspace.getMembers();
        List<WorkspaceMembersDTO> workspaceMembersDTOS = workspaceMemberMapper.toWorkspaceMembersDTOs(workspaceMembers);
        List<WorkspaceMembersDTO> admins = workspaceMembersDTOS.stream().filter(m -> WorkspaceMemberRole.valueOf(m.getUserRole()).equals(WorkspaceMemberRole.WORKSPACE_ADMIN)).collect(Collectors.toList());
        List<WorkspaceMembersDTO> members = workspaceMembersDTOS.stream().filter(m -> WorkspaceMemberRole.valueOf(m.getUserRole()).equals(WorkspaceMemberRole.WORKSPACE_MEMBER)).collect(Collectors.toList());
        List<WorkspaceMembersDTO> membersDTOS = new ArrayList<>(admins);
        membersDTOS.addAll(members);
        model.addAttribute(MEMBERS, membersDTOS);
        return "members";
    }

    public String getWorkspaceSettings() {
        return "settings";
    }

    public void populateModel(Long id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        WorkspaceMemberDTO workspaceMember = getWorkspaceMember(id, session);
        model.addAttribute(ModelAttributes.WORKSPACE, workspaceMember.getWorkspaceDTO());
        model.addAttribute(ModelAttributes.CUR_USER_WORKSPACE_ROLE, workspaceMember.getMemberRole());
        model.addAttribute(ModelAttributes.WORKSPACE_MEMBER_ROLES, WorkspaceMemberRole.toSet());
    }

    private WorkspaceMemberDTO getWorkspaceMember(Long id, HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute(CUR_USER);
        WorkspaceMember workspaceMember = workspaceMemberService.getById(id, currentUser.getUsername());
        return workspaceMemberMapper.toWorkspaceMemberDTO(workspaceMember);
    }
}
