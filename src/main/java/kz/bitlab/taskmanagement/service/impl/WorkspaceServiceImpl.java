package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.entity.User;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.enums.WorkspaceMemberRole;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.repository.WorkspaceRepository;
import kz.bitlab.taskmanagement.service.BoardService;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.service.WorkspaceMemberService;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import kz.bitlab.taskmanagement.util.PropertiesReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private static final Properties PROPERTIES = PropertiesReader.getRuProperties();

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberService workspaceMemberService;
    private final UserService userService;
    private final BoardService boardService;

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
        User user = userService.getByUsernameOrElseThrow(username);
        if (user == null) {
            throw new NotFoundException("User is not found!");
        }

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

    @Override
    public void updateWorkspaceName(Long id, String newWorkspaceName) {
        if (id == null) throw new BadRequestException("id is null");
        if (newWorkspaceName == null || newWorkspaceName.isEmpty())
            throw new BadRequestException("workspace name is empty");

        Workspace workspace = getById(id);
        workspace.setTitle(newWorkspaceName);
        workspaceRepository.save(workspace);
    }

    @Override
    public void deleteById(Long id) {
        Workspace workspace = getById(id);
        workspaceRepository.delete(workspace);
    }
}
