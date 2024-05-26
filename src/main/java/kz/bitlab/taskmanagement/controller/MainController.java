package kz.bitlab.taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kz.bitlab.taskmanagement.dto.UserDTO;
import kz.bitlab.taskmanagement.service.UserService;
import kz.bitlab.taskmanagement.util.SessionAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String mainPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute(SessionAttribute.CUR_USER);
        return "main";
    }
}
