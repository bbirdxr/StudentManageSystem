package cn.edu.seu.historycontest;

import cn.edu.seu.historycontest.controller.ChoiceQuestionController;
import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.entity.Department;
import cn.edu.seu.historycontest.entity.JudgeQuestion;
import cn.edu.seu.historycontest.mapper.ChoiceQuestionMapper;
import cn.edu.seu.historycontest.mapper.JudgeQuestionMapper;
import cn.edu.seu.historycontest.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@SpringBootTest
class HistoryContestApplicationTests {

    @Autowired
    private DepartmentService departmentService;


    void insertDepartment() {
        departmentService.save(new Department("01", "建筑学类"));
        departmentService.save(new Department("G1", "机械能源材料类"));
        departmentService.save(new Department("GS", "工商管理类"));
        departmentService.save(new Department("G3", "环境化工生物类"));
        departmentService.save(new Department("D1", "电子信息类"));
        departmentService.save(new Department("D2", "电子信息类(无锡)"));
        departmentService.save(new Department("TJ", "土木交通类"));
        departmentService.save(new Department("G2", "自动化电气测控类"));
        departmentService.save(new Department("JS", "计算机类"));
        departmentService.save(new Department("LK", "理科实验班"));
        departmentService.save(new Department("11", "生物医学类"));
        departmentService.save(new Department("13", "人文科学试验班"));
        departmentService.save(new Department("14", "经济学类"));
        departmentService.save(new Department("17", "外国语类"));
        departmentService.save(new Department("24", "艺术类"));
        departmentService.save(new Department("42", "预防医学类"));
        departmentService.save(new Department("43", "临床医学类"));
        departmentService.save(new Department("61", "吴健雄学院"));
    }


}
