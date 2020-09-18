package cn.edu.seu.historycontest.service;

import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.payload.GetPageResponse;
import cn.edu.seu.historycontest.payload.StudentListResponse;
import cn.edu.seu.historycontest.security.UserPrincipal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
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
    void insertStudent(User user);
    void insertStudents(List<User> users);
    void insertAdmin(User user);
    void insertAdmins(List<User> admins);
    void deleteAllStudents();
    void deleteAllAdmins();
    void deleteStudent(Long id);
    void deleteStudents(List<Long> ids);
    void editStudent(User user);
    void editAdmin(User user);
    void changePassword(UserPrincipal user, String oldPassword, String newPassword);

    List<User> getAllStudent();
    List<StudentListResponse> getStudentList();
    List<User> getAdminList();
    GetPageResponse getStudentPage(long current, long size);
    GetPageResponse getStudentPage(long current, long size, long department);
    GetPageResponse getStudentPage(long current, long size, String queryType, String queryValue);
    GetPageResponse getStudentPage(long current, long size, String queryType, String queryValue, long department);
    User getStudentBySid(String sid);
    User getByCardId(String cardId);

    Integer getStudentCount();
}
