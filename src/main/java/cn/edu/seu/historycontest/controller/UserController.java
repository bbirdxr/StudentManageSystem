package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public void insert() {
        User user = new User();
        user.setSid("JS219109");
        user.setName("李梅");
        user.setCardId("213102");
        user.setRole("学生");
        userService.save(user);
    }

}

