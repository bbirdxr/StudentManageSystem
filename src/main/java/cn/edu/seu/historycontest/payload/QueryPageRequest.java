package cn.edu.seu.historycontest.payload;

import lombok.Data;

@Data
public class QueryPageRequest {
    private Integer pageIndex;
    private Integer pageSize;
    private String queryType;
    private String queryValue;
}
