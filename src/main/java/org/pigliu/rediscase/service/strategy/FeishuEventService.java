package org.pigliu.rediscase.service.strategy;

import io.lettuce.core.internal.LettuceLists;
import lombok.extern.slf4j.Slf4j;
import org.pigliu.rediscase.service.function.FeishuMessage;
import org.pigliu.rediscase.service.function.Message;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.EventObject;
import java.util.List;

/**
 * 消息事件处理
 *
 * @author liufuqiang
 * @since 2023/7/11
 */
@Slf4j
@Service
public class FeishuEventService implements EventStrategy<Message> {

    @Override
    public boolean handleMessage(Message feishuMessage) {
        log.info("handle wechat messags{}", feishuMessage.toString());
        return true;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.FEISHU;
    }
}
