package kz.bitlab.taskmanagement.service;

import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.entity.key.WorkspaceMemberKey;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;

import java.util.List;

public interface WorkspaceMemberService {

    default WorkspaceMemberKey id(Workspace workspace, User user) {
        if (workspace.getId() == null) throw new BadRequestException("Workspace id is null");
        if (user.getId() == null) throw new BadRequestException("User id is null");

        return new WorkspaceMemberKey(workspace.getId(), user.getId());
    }

    WorkspaceMember create(Workspace workspace, User user, WorkspaceMemberRole memberRole);

    List<WorkspaceMember> getByUser(String username);

    WorkspaceMember getById(Workspace workspace, User user);

    WorkspaceMember getById(Long workspaceId, String username);

    void delete(WorkspaceMember workspaceMember);
}
