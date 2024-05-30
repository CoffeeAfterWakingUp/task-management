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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserService userService;

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
        Optional<User> userOpt = userService.getByUsername(username);
        return workspaceMemberRepository.findByUser(userOpt.get());
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
}
