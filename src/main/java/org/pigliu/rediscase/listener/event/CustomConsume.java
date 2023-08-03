package org.pigliu.rediscase.listener.event;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/21
 */
@Service
public class CustomConsume {



    @EventListener
    public void consume(CustomEvent customEvent) {
        System.out.println("1111");
    }
}
