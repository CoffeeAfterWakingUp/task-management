package kz.bitlab.taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.controller.adapter.BoardAdapter;
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
public class BoardController {

    private final BoardAdapter boardAdapter;

    @ModelAttribute
    @PreAuthorize("isAuthenticated()")
    public void populateModel(@PathVariable(name = "wId") Long id, HttpServletRequest request, Model model) {
        boardAdapter.populateModel(id, request, model);
    }

    @GetMapping("/{wId}/boards/{bId}")
    @PreAuthorize("isAuthenticated()")
    public String boardPage(@PathVariable Long wId,
                            @PathVariable Long bId,
                            Model model,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        return boardAdapter.boardPage(wId, bId, model, session);
    }

}
