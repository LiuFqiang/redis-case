package org.pigliu.rediscase.dto.request;

import lombok.Data;

@Data
public class TaskResponse {
    private String requestId;
    private Integer taskId;
}
