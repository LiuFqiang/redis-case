package org.pigliu.rediscase.service.function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.pigliu.rediscase.service.strategy.MessageType;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/14
 */
@Data
@Builder
public class Message {

    private String id;

    private MessageType type;

    private String body;

    public String getName() {
        return "message";
    }
}
