package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.payload.EditStudentRequest;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("student/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getStudentList() {
        return userService.getAllStudent();
    }

    @PutMapping("student/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public void editStudent(@RequestBody EditStudentRequest student) {
        User user = new User();
        BeanUtils.copyProperties(student, user);
        userService.editStudent(user);
    }

    @DeleteMapping("student/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Long id) {
        userService.removeById(id);
    }

    @PutMapping("student/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public void insertStudent(@RequestBody EditStudentRequest student) {
        User user = new User();
        BeanUtils.copyProperties(student, user);
        user.setRole(Constants.ROLE_STUDENT);
        user.setStatus(Constants.STATUS_NOT_START);
        userService.save(user);
    }

}

