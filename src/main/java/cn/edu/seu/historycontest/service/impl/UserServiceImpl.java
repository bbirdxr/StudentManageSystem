package cn.edu.seu.historycontest.service.impl;

import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.mapper.UserMapper;
import cn.edu.seu.historycontest.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
