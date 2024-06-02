package kz.bitlab.taskmanagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.*;
import kz.bitlab.taskmanagement.rest.adapter.WorkspaceRestAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceRestController {

    private final WorkspaceRestAdapter workspaceAdapter;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<WorkspaceDTO>> create(@RequestBody CreateWorkspaceDTO createWorkspaceDTO,
                                                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        ApiResponse<WorkspaceDTO> apiResponse = workspaceAdapter.create(createWorkspaceDTO, session);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @PostMapping("/{id}/members")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> addUser(@RequestBody AddUserToWorkspaceDTO addUserToWorkspaceDTO,
                                                        @PathVariable Long id) {
        ApiResponse<Boolean> apiResponse = workspaceAdapter.addUser(addUserToWorkspaceDTO, id);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @PatchMapping("/{id}/workspace-name")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> editWorkspaceName(@PathVariable Long id,
                                                                  @RequestBody EditWorkspaceNameDTO editWorkspaceNameDTO) {
        ApiResponse<Boolean> apiResponse = workspaceAdapter.editWorkspaceName(id, editWorkspaceNameDTO);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Boolean>> deleteWorkspace(@PathVariable Long id) {
        ApiResponse<Boolean> apiResponse = workspaceAdapter.deleteWorkspace(id);
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getStatus()));
    }


}
