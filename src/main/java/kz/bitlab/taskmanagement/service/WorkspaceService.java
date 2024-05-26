package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;

public interface WorkspaceService {

    WorkspaceDTO create(CreateWorkspaceDTO createWorkspaceDTO);

}
