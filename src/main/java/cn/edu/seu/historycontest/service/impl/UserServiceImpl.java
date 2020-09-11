package cn.edu.seu.historycontest.service.impl;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.config.SecurityConfig;
import cn.edu.seu.historycontest.entity.Department;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.exception.ForbiddenException;
import cn.edu.seu.historycontest.mapper.UserMapper;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.DepartmentService;
import cn.edu.seu.historycontest.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<User> getAllStudent() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);
        return list(queryWrapper);
    }

    @Override
    public Page<User> getStudentPage(long current, long size) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);

        page(page, queryWrapper);
        return page;
    }

    @Override
    public void editStudent(User user) {
        fixUser(user);
        updateById(user);
    }

    private void fixUser(User user) {
        user.setSid(user.getSid().replaceAll("\\s*", ""));
        user.setCardId(user.getCardId().replaceAll("\\s*", ""));

        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("prefix", user.getSid().substring(0, 2));
        Department department = departmentService.getOne(queryWrapper);
        if (department != null)
            user.setDepartment(department.getId());
    }

    @Override
    public void insertStudent(User user) {
        user.setRole(Constants.ROLE_STUDENT);
        user.setStatus(Constants.STATUS_NOT_START);
        fixUser(user);
        save(user);
    }

    @Override
    public User getStudentBySid(String sid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", sid.replaceAll("\\s*", ""));
        return getOne(queryWrapper);
    }

    @Override
    public User getStudentByCardId(String cardId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId.replaceAll("\\s*", ""));
        return getOne(queryWrapper);
    }

    @Override
    public void changePassword(UserPrincipal user, String oldPassword, String newPassword) {
        if (!SecurityConfig.bCryptPasswordEncoder.matches(oldPassword, user.getPassword()))
            throw new ForbiddenException("原密码错误");
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setPassword(SecurityConfig.bCryptPasswordEncoder.encode(newPassword));
        updateById(newUser);
    }
}
