package org.pigliu.rediscase.service.strategy;

import lombok.extern.slf4j.Slf4j;
import org.pigliu.rediscase.service.function.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.EventObject;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/11
 */
@Slf4j
@Service
public class WechatEventService implements EventStrategy<Message> {

    @Override
    public boolean handleMessage(Message message) {
        log.info("handle wechat message{}", message.toString());
        return false;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.WECHAT;
    }
}
