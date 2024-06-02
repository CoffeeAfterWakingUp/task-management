package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.Workspace;

public interface WorkspaceService {

    Workspace create(Workspace workspace, String username);

    Workspace getById(Long id);

    void updateWorkspaceName(Long id, String newWorkspaceName);

    void deleteById(Long id);

}
