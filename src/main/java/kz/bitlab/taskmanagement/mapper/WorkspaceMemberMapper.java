package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.UserWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceMemberDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceMembersDTO;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = WorkspaceMapper.class)
public interface WorkspaceMemberMapper {

    @Mapping(target = "workspaceDTO", source = "workspaceMember.workspace")
    List<UserWorkspaceDTO> toDTOs(List<WorkspaceMember> workspaceMembers);

    @Mapping(target = "workspaceDTO", source = "workspaceMember.workspace")
    UserWorkspaceDTO toDTO(WorkspaceMember workspaceMember);

    List<WorkspaceMembersDTO> toWorkspaceMembersDTOs(Set<WorkspaceMember> workspaceMembers);

    @Mapping(target = "userFullName", source = "workspaceMember.user.fullName")
    @Mapping(target = "userUsername", source = "workspaceMember.user.username")
    @Mapping(target = "userRole", expression = "java(workspaceMember.getMemberRole().name())")
    WorkspaceMembersDTO toWorkspaceMembersDTO(WorkspaceMember workspaceMember);

    @Mapping(target = "workspaceDTO", source = "workspaceMember.workspace")
    @Mapping(target = "memberRole", expression = "java(workspaceMember.getMemberRole().name())")
    WorkspaceMemberDTO toWorkspaceMemberDTO(WorkspaceMember workspaceMember);
}
