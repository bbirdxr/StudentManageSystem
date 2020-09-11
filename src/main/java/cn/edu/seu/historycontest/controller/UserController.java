package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.payload.*;
import cn.edu.seu.historycontest.security.CurrentUser;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.PaperService;
import cn.edu.seu.historycontest.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private PaperService paperService;

    @GetMapping
    public User getInfo(@CurrentUser UserPrincipal userPrincipal) {
        User user = new User();
        BeanUtils.copyProperties(userPrincipal, user);
        user.setPassword(null);
        return user;
    }

    @PostMapping("student/page")
    @PreAuthorize("hasRole('ADMIN')")
    public GetPageResponse getStudentPage(@RequestBody GetPageRequest pageRequest) {
        GetPageResponse pageResponse = new GetPageResponse();
        Page<User> page = userService.getStudentPage(pageRequest.getPageIndex(), pageRequest.getPageSize());
        pageResponse.setTotal(page.getTotal());
        pageResponse.setList(page.getRecords().stream().map(user ->
                StudentListResponse.ofUser(user, paperService.getScore(user.getId()))).collect(Collectors.toList()));
        return pageResponse;
    }

    @PostMapping("student/query")
    @PreAuthorize("hasRole('ADMIN')")
    public GetPageResponse getStudentPageWithCondition(@RequestBody QueryPageRequest pageRequest) {
        GetPageResponse pageResponse = new GetPageResponse();
        Page<User> page = userService.getStudentPage(pageRequest.getPageIndex(), pageRequest.getPageSize(), pageRequest.getQueryType(), pageRequest.getQueryValue());
        pageResponse.setTotal(page.getTotal());
        pageResponse.setList(page.getRecords().stream().map(user ->
                StudentListResponse.ofUser(user, paperService.getScore(user.getId()))).collect(Collectors.toList()));
        return pageResponse;
    }

    @GetMapping("student/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<StudentListResponse> getStudentList() {
        List<User> student = userService.getAllStudent();
        return student.stream().map(user ->
                StudentListResponse.ofUser(user, paperService.getScore(user.getId()))).collect(Collectors.toList());
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

    @DeleteMapping("student")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudents(@RequestBody List<Long> ids) {
        userService.removeByIds(ids);
    }

    @PutMapping("student/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public void insertStudent(@RequestBody EditStudentRequest student) {
        User user = new User();
        BeanUtils.copyProperties(student, user);
        userService.insertStudent(user);
    }

    @PutMapping("password")
    @PreAuthorize("hasRole('ADMIN')")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @CurrentUser UserPrincipal userPrincipal) {
        userService.changePassword(userPrincipal, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
    }

}

