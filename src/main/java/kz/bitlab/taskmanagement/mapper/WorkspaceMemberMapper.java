package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.UserWorkspaceDTO;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = WorkspaceMapper.class)
public interface WorkspaceMemberMapper {

    @Mapping(target = "workspaceDTO", source = "workspaceMember.workspace")
    List<UserWorkspaceDTO> toDTOs(List<WorkspaceMember> workspaceMembers);

    @Mapping(target = "workspaceDTO", source = "workspaceMember.workspace")
    UserWorkspaceDTO toDTO(WorkspaceMember workspaceMember);
}
