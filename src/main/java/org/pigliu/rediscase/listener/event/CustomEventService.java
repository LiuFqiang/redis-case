package org.pigliu.rediscase.listener.event;

import lombok.AllArgsConstructor;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/21
 */
@Service
@AllArgsConstructor
public class CustomEventService {

    private final ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void publish() {
        System.out.println("publishing event");
        applicationEventPublisher.publishEvent(new CustomEvent(this));
    }
}
