package cn.edu.seu.historycontest.service;

import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.security.UserPrincipal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
public interface UserService extends IService<User> {
    List<User> getAllStudent();
    Page<User> getStudentPage(long current, long size);
    void editStudent(User user);
    void insertStudent(User user);
    User getStudentBySid(String sid);
    User getStudentByCardId(String cardId);
    void changePassword(UserPrincipal user, String oldPassword, String newPassword);
}
