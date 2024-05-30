package kz.bitlab.taskmanagement.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.adapter.WorkspaceAdapter;
import kz.bitlab.taskmanagement.dto.AddUserToWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.ApiResponse;
import kz.bitlab.taskmanagement.dto.CreateWorkspaceDTO;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceRestController {

    private final WorkspaceAdapter workspaceAdapter;

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

}
