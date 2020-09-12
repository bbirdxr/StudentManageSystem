package cn.edu.seu.historycontest.excel.entity;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.payload.StudentListResponse;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StudentExportEntity {

    @ExcelProperty("学号")
    private String sid;
    @ExcelProperty("一卡通号")
    private String cardId;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("院系")
    private String department;
    @ExcelProperty("分数")
    private Integer score;

}
