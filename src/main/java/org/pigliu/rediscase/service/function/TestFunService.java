package org.pigliu.rediscase.service.function;

import cn.hutool.core.lang.Editor;
import org.pigliu.rediscase.dto.BookInfo;

import java.time.Clock;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/13
 */
public class TestFunService {

    public static void main(String[] args) {
//        TestFunService.build(msg -> System.out.println(msg + "11"));
        Clock clock = Clock.systemUTC();
        System.out.println(clock.getZone());
    }

    public static BookInfo build(CustomFunction customFunction) {
        customFunction.sendMsg("111");
        return new BookInfo();
    }
}
