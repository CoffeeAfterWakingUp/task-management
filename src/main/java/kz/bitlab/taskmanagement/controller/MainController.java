package kz.bitlab.taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.adapter.MainAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final MainAdapter mainAdapter;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String mainPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        return mainAdapter.mainPage(session, model);
    }
}
