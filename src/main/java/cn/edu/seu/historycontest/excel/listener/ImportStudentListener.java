package cn.edu.seu.historycontest.excel.listener;

import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.excel.entity.ExportStudentEntity;
import cn.edu.seu.historycontest.excel.entity.ImportStudentEntity;
import cn.edu.seu.historycontest.exception.StudentAlreadyExistsException;
import cn.edu.seu.historycontest.service.UserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;

public class ImportStudentListener extends AnalysisEventListener<ImportStudentEntity> {

    private final UserService userService;
    private final List<User> list = new LinkedList<>();

    public ImportStudentListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void invoke(ImportStudentEntity studentExcelEntity, AnalysisContext analysisContext) {
        if (null != userService.getStudentBySid(studentExcelEntity.getSid()))
            throw StudentAlreadyExistsException.bySid(studentExcelEntity.getSid());
        if (null != userService.getStudentByCardId(studentExcelEntity.getCardId()))
            throw StudentAlreadyExistsException.byCardId(studentExcelEntity.getCardId());

        User user = new User();
        user.setSid(studentExcelEntity.getSid());
        user.setCardId(studentExcelEntity.getCardId());
        user.setName(studentExcelEntity.getName());
        list.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        userService.insertStudents(list);
    }
}
