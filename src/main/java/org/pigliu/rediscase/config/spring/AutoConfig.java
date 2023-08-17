package org.pigliu.rediscase.config.spring;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/10
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "pig", name = "enabled", havingValue = "true")
public class AutoConfig implements InitializingBean {



    private static final int[][] DAY_AMOUNT = {
            {0, 3}, {1, 5}, {2, 6}, {3, 6}, {4, 8}, {5, 8}, {6, 10}
    };

    private static final boolean[] status_ch = {true, false, false, false, false, false, false};

    public static void main(String[] args) {
        int dayOfWeek = 1;
        List<Map> list = new ArrayList<>();
        boolean[] status = status_ch;
        int continueDay = 0;
        for (int i = 0; i < dayOfWeek; i++) {
            if (status[i]) {
                continueDay ++;
            } else if (i != dayOfWeek - 1){
                continueDay = 0;
            }
        }

        int dayAmount = DAY_AMOUNT[0][1];
        boolean todayCheck = false;
        for (int i = 0; i < 7; i++) {
            Map map = new HashMap(4);
            boolean isCheck = status[i];
            map.put("isCheck", isCheck);
            map.put("amount", DAY_AMOUNT[0][1]);
            if (i == dayOfWeek - 1) {
                todayCheck = isCheck;
                map.put("amount", DAY_AMOUNT[todayCheck ? continueDay - 1 : continueDay][1]);
            }
            if (i == dayOfWeek) {
                map.put("amount", DAY_AMOUNT[todayCheck ? continueDay : continueDay + 1][1]);
            }

        }

        System.out.println("dayAmount: " + dayAmount);
        System.out.println("continueDay: " + continueDay);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("auto-config is inited");
    }
}
