package kz.bitlab.taskmanagement.rest;

import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.rest.adapter.WorkspaceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceRestController {

    private final WorkspaceAdapter workspaceAdapter;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceDTO>> create(@RequestBody CreateWorkspaceDTO createWorkspaceDTO) {
        ApiResponse<WorkspaceDTO> apiResponse = workspaceAdapter.create(createWorkspaceDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

}
