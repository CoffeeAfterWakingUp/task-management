package kz.bitlab.taskmanagement.controller;

import kz.bitlab.taskmanagement.adapter.WorkspaceAdapter;
import kz.bitlab.taskmanagement.dto.WorkspaceDTO;
import kz.bitlab.taskmanagement.enums.BoardVisibility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceAdapter workspaceAdapter;
    
    @GetMapping("/{id}/boards")
    @PreAuthorize("isAuthenticated()")
    public String getWorkspaceBoards(@PathVariable Long id, Model model) {
        WorkspaceDTO workspaceDTO = workspaceAdapter.getWorkspaceBoards(id);
        Set<String> boardVisibilities = BoardVisibility.toSet();
        model.addAttribute("workspace", workspaceDTO);
        model.addAttribute("boardVisibilities", boardVisibilities);
        return "workspace";
    }
    
    
}
