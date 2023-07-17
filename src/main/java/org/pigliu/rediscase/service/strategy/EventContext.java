package org.pigliu.rediscase.service.strategy;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.pigliu.rediscase.service.function.Message;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/11
 */
@Slf4j
@Service
public class EventContext  {

    private static Map<String, EventStrategy> STRATEGY_MAP = new HashMap<>(8);

    public EventContext(List<EventStrategy> messageServices) {
        messageServices.forEach(service ->
                service.getMessageTypes().forEach(type -> STRATEGY_MAP.put(((MessageType) type).name(), service)));
    }

    public EventStrategy getEvent(@NonNull MessageType messageType) {
        return STRATEGY_MAP.get(messageType.name());
    }

    public boolean consumeEventMessage(Message message) {
        STRATEGY_MAP.get(message.getType()).handleMessage(message);
        return true;
    }
}
