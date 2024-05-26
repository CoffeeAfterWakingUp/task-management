package kz.bitlab.taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.bitlab.taskmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {
        String msg = authService.loginError(request);
        model.addAttribute("msg", msg);
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(HttpServletRequest request, Authentication authentication, Model model) {
        String msg = authService.loginSuccess(request, authentication);
        if (msg != null && msg.equals("ok")) {
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

}
