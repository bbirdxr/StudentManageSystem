package cn.edu.seu.historycontest.service.impl;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.config.SecurityConfig;
import cn.edu.seu.historycontest.entity.Department;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.exception.ForbiddenException;
import cn.edu.seu.historycontest.exception.StudentAlreadyExistsException;
import cn.edu.seu.historycontest.mapper.UserMapper;
import cn.edu.seu.historycontest.payload.GetPageResponse;
import cn.edu.seu.historycontest.payload.StudentListResponse;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.DepartmentService;
import cn.edu.seu.historycontest.service.PaperService;
import cn.edu.seu.historycontest.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PaperService paperService;

    @Override
    public List<User> getAllStudent() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);
        return list(queryWrapper);
    }

    @Override
    public List<StudentListResponse> getStudentList() {
        List<User> student = getAllStudent();
        return student.stream().map(user ->
                StudentListResponse.ofUser(user, paperService.getScore(user.getId()))).collect(Collectors.toList());
    }

    @Override
    public GetPageResponse getStudentPage(long current, long size) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);

        return queryPage(page, queryWrapper);
    }

    @Override
    public GetPageResponse getStudentPage(long current, long size, String queryType, String queryValue) {
        Page<User> page = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);
        if (!StringUtils.isEmpty(queryType) && !StringUtils.isEmpty(queryValue)) {
            if ("status".equals(queryType)) {
                if (Constants.STATUS_NOT_SUBMITTED.equals(queryValue))
                    queryWrapper.ne(queryType, Constants.STATUS_SUBMITTED);
                else
                    queryWrapper.eq(queryType, Constants.STATUS_SUBMITTED);
            }
            else
                queryWrapper.eq(queryType, queryValue);
        }
        return queryPage(page, queryWrapper);
    }

    private GetPageResponse queryPage(Page<User> page, QueryWrapper<User> queryWrapper) {
        page(page, queryWrapper);

        GetPageResponse pageResponse = new GetPageResponse();
        pageResponse.setTotal(page.getTotal());
        pageResponse.setList(page.getRecords().stream().map(user ->
                StudentListResponse.ofUser(user, paperService.getScore(user.getId()))).collect(Collectors.toList()));
        return pageResponse;
    }

    @Override
    public void editStudent(User user) {
        if (null != getStudentBySid(user.getSid()))
            throw new ForbiddenException("学号已存在");
        if (null != getStudentByCardId(user.getCardId()))
            throw new ForbiddenException("一卡通号已存在");

        fixStudent(user);
        updateById(user);
    }

    private void fixStudent(User user) {
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
        if (null != getStudentBySid(user.getSid()))
            throw StudentAlreadyExistsException.bySid(user.getSid());
        if (null != getStudentByCardId(user.getCardId()))
            throw StudentAlreadyExistsException.byCardId(user.getCardId());

        user.setRole(Constants.ROLE_STUDENT);
        user.setStatus(Constants.STATUS_NOT_START);
        fixStudent(user);
        save(user);
    }

    @Override
    public void insertStudents(List<User> users) {
        for (User user : users) {
            if (null != getStudentBySid(user.getSid()))
                throw StudentAlreadyExistsException.bySid(user.getSid());
            if (null != getStudentByCardId(user.getCardId()))
                throw StudentAlreadyExistsException.byCardId(user.getCardId());

            user.setRole(Constants.ROLE_STUDENT);
            user.setStatus(Constants.STATUS_NOT_START);
            fixStudent(user);
        }
        saveBatch(users);
    }

    @Override
    public void deleteAllStudents() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", Constants.ROLE_STUDENT);
        remove(queryWrapper);
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
    public Integer getStudentCount() {
        return getAllStudent().size();
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
