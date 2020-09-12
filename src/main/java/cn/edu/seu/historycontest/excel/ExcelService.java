package cn.edu.seu.historycontest.excel;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.Department;
import cn.edu.seu.historycontest.excel.entity.StudentExcelEntity;
import cn.edu.seu.historycontest.payload.StudentListResponse;
import cn.edu.seu.historycontest.service.DepartmentService;
import cn.edu.seu.historycontest.service.PaperService;
import cn.edu.seu.historycontest.service.UserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    public void exportStudentList(OutputStream outputStream) {
        List<StudentExcelEntity> list = prepareStudentList();

        WriteCellStyle style = new WriteCellStyle();
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 11);
        style.setWriteFont(font);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(style, style);

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(outputStream).build();
            excelWriter.write(list, EasyExcel.writerSheet("学生列表").head(StudentExcelEntity.class).registerWriteHandler(horizontalCellStyleStrategy).build());
        } finally {
            if (excelWriter != null)
                excelWriter.finish();
        }

    }

    private List<StudentExcelEntity> prepareStudentList() {
        List<StudentListResponse> studentList = userService.getStudentList();
        return studentList.stream().map(response -> {
            StudentExcelEntity studentExcelEntity = new StudentExcelEntity();
            studentExcelEntity.setSid(response.getSid());
            studentExcelEntity.setCardId(response.getCardId());
            studentExcelEntity.setName(response.getName());
            if (Constants.STATUS_SUBMITTED.equals(response.getStatus()))
                studentExcelEntity.setStatus("已提交");
            else
                studentExcelEntity.setStatus("未提交");
            studentExcelEntity.setDepartment(departmentService.getNameById(response.getDepartment()));
            studentExcelEntity.setScore(response.getScore());
            return studentExcelEntity;
        }).collect(Collectors.toList());
    }

}
