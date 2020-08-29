package cn.edu.seu.historycontest.service.impl;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.mapper.UserMapper;
import cn.edu.seu.historycontest.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

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
    private UserMapper userMapper;

    @Override
    public List<User> getAllStudent() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public void editStudent(User user) {
        userMapper.updateById(user);
    }
}
