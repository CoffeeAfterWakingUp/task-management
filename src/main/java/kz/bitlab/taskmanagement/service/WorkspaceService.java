package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.entity.Workspace;

public interface WorkspaceService {

    Workspace create(CreateWorkspaceDTO createWorkspaceDTO);
    Workspace getById(Long id);

}
