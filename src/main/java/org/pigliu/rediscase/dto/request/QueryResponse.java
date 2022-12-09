package org.pigliu.rediscase.dto.request;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class QueryResponse {
    private String requestId;
    private String style;
    private String text;
    private String resolution;
    private Integer status;
    private Integer taskId;
    private List<Image> imgUrls;
    private String createTime;
    private String waiting;
    @Deprecated
    private String img;

    @Setter
    private static class Image {
        private Integer score;
        private String image;
    }
}
