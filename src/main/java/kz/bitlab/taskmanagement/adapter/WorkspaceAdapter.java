package kz.bitlab.taskmanagement.adapter;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.entity.Workspace;
import kz.bitlab.taskmanagement.mapper.WorkspaceMapper;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter {

    private final WorkspaceService workspaceService;
    private final WorkspaceMapper workspaceMapper;

    public ApiResponse<WorkspaceDTO> create(CreateWorkspaceDTO createWorkspaceDTO) {
        Workspace workspace = workspaceService.create(createWorkspaceDTO);
        return ApiResponse.<WorkspaceDTO>builder()
                .body(workspaceMapper.toDTO(workspace))
                .status(HttpStatus.CREATED.value())
                .build();
    }

    public WorkspaceDTO getWorkspaceBoards(Long id) {
        Workspace workspace = workspaceService.getById(id);
        return workspaceMapper.toDTO(workspace);
    }
}
