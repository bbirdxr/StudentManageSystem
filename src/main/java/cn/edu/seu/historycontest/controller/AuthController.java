package cn.edu.seu.historycontest.controller;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.exception.ForbiddenException;
import cn.edu.seu.historycontest.payload.LoginRequest;
import cn.edu.seu.historycontest.payload.LoginResponse;
import cn.edu.seu.historycontest.security.JwtTokenProvider;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getSid(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new LoginResponse(jwt);
    }

    @PostMapping("register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        if (null != userService.getStudentBySid(registerRequest.getSid()))
            throw new ForbiddenException("学号已存在");
        if (null != userService.getStudentByCardId(registerRequest.getCardId()))
            throw new ForbiddenException("一卡通号已存在");
        User user = new User();
        user.setSid(registerRequest.getSid());
        user.setCardId(registerRequest.getCardId());
        user.setName(registerRequest.getName());
        userService.insertStudent(user);
    }
}
