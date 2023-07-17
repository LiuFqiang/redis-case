package org.pigliu.rediscase.service.strategy;

import io.lettuce.core.internal.LettuceLists;
import org.assertj.core.util.Lists;
import org.pigliu.rediscase.service.function.Message;

import java.util.List;

/**
 * 事件策略接口类
 *
 * @author liufuqiang
 * @since 2023/7/11
 */
public interface EventStrategy<T extends Message> {

    /**
     * 处理事件消息
     * @param Message
     * @return
     */
     boolean handleMessage(T Message);

    /**
     * 消息类型
     * @return
     */
     MessageType getMessageType();

    /**
     * 可以支持多个消息类型
     * @return
     */
    default List<MessageType> getMessageTypes() {
        return Lists.newArrayList(getMessageType());
    }

}
