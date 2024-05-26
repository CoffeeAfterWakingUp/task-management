package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.mapper.WorkspaceMapper;
import kz.bitlab.taskmanagement.repository.WorkspaceRepository;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import kz.bitlab.taskmanagement.util.PropertiesReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private static final Properties PROPERTIES = PropertiesReader.getRuProperties();

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    public WorkspaceDTO create(CreateWorkspaceDTO createWorkspaceDTO) {
        if (createWorkspaceDTO == null) {
            throw new BadRequestException(PROPERTIES.getProperty("error.checkData"));
        }

        String title = createWorkspaceDTO.getTitle();
        if (title == null || title.isEmpty()) {
            throw new BadRequestException(PROPERTIES.getProperty("error.workspaceTitleIsEmpty"));
        }
        Workspace workspace = workspaceMapper.toEntity(createWorkspaceDTO);
        Workspace newWorkspace = workspaceRepository.save(workspace);
        return workspaceMapper.toDTO(newWorkspace);
    }
}
