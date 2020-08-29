package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User getInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = new User();
        BeanUtils.copyProperties(userPrincipal, user);
        user.setPassword(null);
        return user;
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getStudentList() {
        return userService.getAllStudent();
    }

}

