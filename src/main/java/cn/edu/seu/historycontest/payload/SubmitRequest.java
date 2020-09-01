package cn.edu.seu.historycontest.payload;

import lombok.Data;

import java.util.List;

@Data
public class SubmitRequest {

    private List<Integer> choice;
    private List<Integer> judge;

}
