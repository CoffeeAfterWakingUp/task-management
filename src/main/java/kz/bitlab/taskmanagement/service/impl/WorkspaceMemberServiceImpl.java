package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.entity.key.WorkspaceMemberKey;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.WorkspaceMemberRepository;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserService userService;
    @Lazy
    private final WorkspaceService workspaceService;

    @Override
    public WorkspaceMember create(Workspace workspace, User user, WorkspaceMemberRole memberRole) {
        if (workspace == null) {
            throw new BadRequestException("Workspace is null");
        }
        if (user == null) {
            throw new BadRequestException("User is null");
        }
        if (memberRole == null) {
            throw new BadRequestException("Member role is null");
        }

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .id(id(workspace, user))
                .workspace(workspace)
                .user(user)
                .memberRole(memberRole)
                .build();
        return workspaceMemberRepository.save(workspaceMember);
    }

    @Override
    public List<WorkspaceMember> getByUser(String username) {
        User user = userService.getByUsernameOrElseThrow(username);
        return workspaceMemberRepository.findByUser(user);
    }

    @Override
    public WorkspaceMember getById(Workspace workspace, User user) {
        if (workspace == null) {
            throw new BadRequestException("Workspace is null");
        }
        if (user == null) {
            throw new BadRequestException("User is null");
        }
        WorkspaceMemberKey id = id(workspace, user);
        return workspaceMemberRepository.findById(id).orElseThrow(() -> new NotFoundException("not found with id"));
    }

    @Override
    public WorkspaceMember getById(Long workspaceId, String username) {
        if (workspaceId == null) {
            throw new BadRequestException("workspaceId is null");
        }
        if (username == null) {
            throw new BadRequestException("username is null");
        }
        Workspace workspace = workspaceService.getById(workspaceId);
        User user = userService.getByUsernameOrElseThrow(username);
        return getById(workspace, user);
    }

    @Override
    public void delete(WorkspaceMember workspaceMember) {
        workspaceMemberRepository.delete(workspaceMember);
    }
}
