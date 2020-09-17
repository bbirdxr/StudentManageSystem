package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.excel.ExcelService;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
    private ExcelService excelService;

    @GetMapping
    public User getInfo(@CurrentUser UserPrincipal userPrincipal) {
        User user = new User();
        BeanUtils.copyProperties(userPrincipal, user);
        user.setPassword(null);
        return user;
    }

    @GetMapping("student/count")
    @PreAuthorize("hasRole('ADMIN')")
    public Integer getStudentCount() {
        return userService.getStudentCount();
    }

    @PostMapping("student/page")
    @PreAuthorize("hasRole('ADMIN')")
    public GetPageResponse getStudentPage(@Valid @RequestBody GetPageRequest pageRequest) {
        return userService.getStudentPage(pageRequest.getPageIndex(), pageRequest.getPageSize());
    }

    @PostMapping("student/query")
    @PreAuthorize("hasRole('ADMIN')")
    public GetPageResponse getStudentPageWithCondition(@Valid @RequestBody QueryPageRequest pageRequest) {
        return userService.getStudentPage(pageRequest.getPageIndex(), pageRequest.getPageSize(), pageRequest.getQueryType(), pageRequest.getQueryValue());
    }

    @GetMapping("student/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<StudentListResponse> getStudentList() {
        return userService.getStudentList();
    }

    @PutMapping("student/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public void editStudent(@Valid @RequestBody EditStudentRequest student) {
        User user = new User();
        BeanUtils.copyProperties(student, user);
        userService.editStudent(user);
    }

    @DeleteMapping("student/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Long id) {
        userService.deleteStudent(id);
    }

    @DeleteMapping("student")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudents(@RequestBody List<Long> ids) {
        userService.deleteStudents(ids);
    }

    @PutMapping("student/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public void insertStudent(@Valid @RequestBody EditStudentRequest student) {
        User user = new User();
        BeanUtils.copyProperties(student, user);
        userService.insertStudent(user);
    }

    @PutMapping("password")
    @PreAuthorize("hasRole('ADMIN')")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @CurrentUser UserPrincipal userPrincipal) {
        userService.changePassword(userPrincipal, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
    }

    @GetMapping("student/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportStudentList(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=data.xlsx");
        excelService.exportStudentList(response.getOutputStream());
    }

    @PostMapping("student/import/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public void importAndInsert(@RequestParam(value = "file") MultipartFile upload) throws IOException {
        excelService.importStudent(upload.getInputStream(), false);
    }

    @PostMapping("student/import/cover")
    @PreAuthorize("hasRole('ADMIN')")
    public void importAndCover(@RequestParam(value = "file") MultipartFile upload) throws IOException {
        excelService.importStudent(upload.getInputStream(), true);
    }

    @DeleteMapping("student/all")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllStudent() {
        userService.deleteAllStudents();
    }
}

