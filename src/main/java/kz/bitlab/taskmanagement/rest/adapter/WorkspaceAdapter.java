package kz.bitlab.taskmanagement.rest.adapter;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter {

    private final WorkspaceService workspaceService;

    public ApiResponse<WorkspaceDTO> create(CreateWorkspaceDTO createWorkspaceDTO) {
        WorkspaceDTO workspaceDTO = workspaceService.create(createWorkspaceDTO);
        return ApiResponse.<WorkspaceDTO>builder()
                .body(workspaceDTO)
                .status(HttpStatus.CREATED.value())
                .build();
    }
}
