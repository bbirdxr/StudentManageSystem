package cn.edu.seu.historycontest.payload;

import lombok.Data;

@Data
public class GetPageRequest {
    private Integer pageIndex;
    private Integer pageSize;
}
