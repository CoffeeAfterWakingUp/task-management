package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.entity.Workspace;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BoardMapper.class)
public interface WorkspaceMapper {

    Workspace toEntity(CreateWorkspaceDTO createWorkspaceDTO);

    WorkspaceDTO toDTO(Workspace workspace);


}
