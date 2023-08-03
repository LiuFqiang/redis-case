package org.pigliu.rediscase.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/25
 */
@Slf4j
//@Component
public class RocketMsg extends ThreadHelp{

    @Override
    public void handleMsg() {
        super.handleMsg();
        log.info("rocket");
    }
}
