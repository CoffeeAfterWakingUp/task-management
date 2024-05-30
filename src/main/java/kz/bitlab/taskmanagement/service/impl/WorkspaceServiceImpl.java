package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.entity.WorkspaceMember;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.WorkspaceRepository;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import kz.bitlab.taskmanagement.util.PropertiesReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private static final Properties PROPERTIES = PropertiesReader.getRuProperties();

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberService workspaceMemberService;
    private final UserService userService;

    @Override
    public Workspace create(Workspace workspace, String username) {
        if (workspace == null) {
            throw new BadRequestException(PROPERTIES.getProperty("error.checkData"));
        }
        if (username == null || username.isEmpty()) {
            throw new BadRequestException("username is empty!");
        }

        String title = workspace.getTitle();
        if (title == null || title.isEmpty()) {
            throw new BadRequestException(PROPERTIES.getProperty("error.workspaceTitleIsEmpty"));
        }
        Optional<User> userOpt = userService.getByUsername(username);
        if (userOpt.isEmpty()) {
            throw new NotFoundException("User is not found!");
        }
        User user = userOpt.get();

        workspaceRepository.save(workspace);
        workspaceMemberService.create(workspace, user, WorkspaceMemberRole.WORKSPACE_ADMIN);
        return workspace;
    }

    @Override
    public Workspace getById(Long id) {
        if (id == null) {
            throw new BadRequestException(PROPERTIES.getProperty("error.checkData"));
        }
        Optional<Workspace> workspaceOpt = workspaceRepository.findById(id);
        if (workspaceOpt.isEmpty()) {
            throw new NotFoundException("Workspace not found!");
        }
        return workspaceOpt.get();
    }
}
