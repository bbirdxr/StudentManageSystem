package cn.edu.seu.historycontest.payload;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CalibrateTimeRequest {

    private Long paperId;
    private Date time;

}
