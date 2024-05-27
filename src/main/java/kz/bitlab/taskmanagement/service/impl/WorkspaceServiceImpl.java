package kz.bitlab.taskmanagement.service.impl;

import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.exception.BadRequestException;
import kz.bitlab.taskmanagement.exception.NotFoundException;
import kz.bitlab.taskmanagement.mapper.WorkspaceMapper;
import kz.bitlab.taskmanagement.repository.WorkspaceRepository;
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
    private final WorkspaceMapper workspaceMapper;

    @Override
    public Workspace create(CreateWorkspaceDTO createWorkspaceDTO) {
        if (createWorkspaceDTO == null) {
            throw new BadRequestException(PROPERTIES.getProperty("error.checkData"));
        }

        String title = createWorkspaceDTO.getTitle();
        if (title == null || title.isEmpty()) {
            throw new BadRequestException(PROPERTIES.getProperty("error.workspaceTitleIsEmpty"));
        }
        Workspace workspace = workspaceMapper.toEntity(createWorkspaceDTO);
        return workspaceRepository.save(workspace);
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
