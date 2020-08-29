package cn.edu.seu.historycontest.payload;

import lombok.Data;

@Data
public class EditStudentRequest {

    private Long id;
    private String sid;
    private String cardId;
    private String name;

}
