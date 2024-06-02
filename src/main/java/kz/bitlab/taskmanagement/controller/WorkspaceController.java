package kz.bitlab.taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.controller.adapter.WorkspaceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceAdapter workspaceAdapter;

    @ModelAttribute
    @PreAuthorize("isAuthenticated()")
    public void populateModel(@PathVariable Long id, HttpServletRequest request, Model model) {
        workspaceAdapter.populateModel(id, request, model);
    }

    @GetMapping("/{id}/boards")
    @PreAuthorize("isAuthenticated()")
    public String getWorkspaceBoards(@PathVariable Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return workspaceAdapter.getWorkspaceBoards(id, model, session);
    }

    @GetMapping("/{id}/members")
    @PreAuthorize("isAuthenticated()")
    public String getWorkspaceMembers(@PathVariable Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return workspaceAdapter.getWorkspaceMembers(id, model, session);
    }

    @GetMapping("/{id}/settings")
    @PreAuthorize("isAuthenticated()")
    public String getWorkspaceSettings(@PathVariable Long id) {
        return workspaceAdapter.getWorkspaceSettings();
    }


}
