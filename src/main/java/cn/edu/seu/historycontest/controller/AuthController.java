package cn.edu.seu.historycontest.controller;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.exception.ForbiddenException;
import cn.edu.seu.historycontest.payload.ChangePasswordRequest;
import cn.edu.seu.historycontest.payload.LoginRequest;
import cn.edu.seu.historycontest.payload.LoginResponse;
import cn.edu.seu.historycontest.security.CurrentUser;
import cn.edu.seu.historycontest.security.JwtTokenProvider;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.AuthService;
import cn.edu.seu.historycontest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return new LoginResponse(authService.login(loginRequest.getSid(), loginRequest.getPassword(), loginRequest.getCode()));
    }

    @PostMapping("register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest.getSid(), registerRequest.getCardId(), registerRequest.getName());
    }
}
