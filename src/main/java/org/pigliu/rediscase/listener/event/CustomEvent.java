package org.pigliu.rediscase.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/21
 */
public class CustomEvent extends ApplicationEvent {

    String msg;

    public CustomEvent(Object source) {
        super(source);
    }
}
