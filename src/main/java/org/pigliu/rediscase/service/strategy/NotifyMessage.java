package org.pigliu.rediscase.service.strategy;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.EventObject;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/11
 */
public class NotifyMessage extends ApplicationEvent {

    private String name;


    public NotifyMessage(Object source) {
        super(source);
    }
}
