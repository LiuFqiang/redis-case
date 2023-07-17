package org.pigliu.rediscase.service.strategy;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.pigliu.rediscase.service.function.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/17
 */
@Slf4j
@Service
public class MailEventService implements EventStrategy<Message> {

    @Override
    public boolean handleMessage(Message Message) {
        log.info("handle mail message{}", Message.toString());
        return false;
    }

    @Override
    public List<MessageType> getMessageTypes() {
        return Lists.newArrayList(MessageType.MAIL_163, MessageType.MAIL_QQ);
    }

    @Override
    public MessageType getMessageType() {
        return null;
    }
}
