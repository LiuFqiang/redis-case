package org.pigliu.rediscase.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.dtflys.forest.http.ForestResponse;
import io.netty.util.concurrent.CompleteFuture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.inject.Inject;
import org.pigliu.rediscase.annotation.Signature;
import org.pigliu.rediscase.dto.R;
import org.pigliu.rediscase.mapper.DynamicMapper;
import org.pigliu.rediscase.service.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@RestController
@RequestMapping("/test")
@Slf4j
@Validated
@RequiredArgsConstructor
public class TestController implements BeanFactoryAware, BeanNameAware, BeanPostProcessor {
    private final DynamicSelectService dynamicSelectService;

    private final ThreadPoolService threadPoolService;

    private final RedisPipleService redisPipleService;

    @Autowired
    @Qualifier("rocket")
    private  Queue queue;

    private final Environment environment;

    private final DynamicMapper dynamicMapper;


    @GetMapping("/testGet")
    public R testGetSign(@RequestParam String username) {
        log.info("params :{}", username);
        System.out.println(Thread.currentThread().getName());
        return R.ok("success");
    }

    @PostMapping("/testPost")
    @Signature
    public R testPostSign(@RequestBody Map<String, String> params) {
        log.info("params: {}", params);
        return R.ok("success");
    }

    @PostMapping("/test3")
    @Signature
    public Object testPostSign2(@RequestParam(value = "usernames")   String usernames,
                                @RequestParam(value = "bookId")  String bookId) {
        return R.ok();
    }

    @GetMapping("/test4")
    public Object queryList() {
        dynamicSelectService.queryList();
        return R.ok();
    }

    @GetMapping("/testPool")
    public Object testPool() {
        threadPoolService.testShutdown();
        return R.ok();
    }

    @GetMapping("/json1")
    public Object testPar(final Integer a) {
        return R.ok();
    }

    @GetMapping("/testInter")
    public Object testInter() {
//        queue.sendMsg();
        String[] profiles = environment.getActiveProfiles();
        return R.ok();
    }

    @GetMapping("/updateSql")
    public Object updateSql() {
        redisPipleService.testBit();
        return R.ok();
    }

    @GetMapping("/test")
    public Object testForkJoin(int seconds) throws ExecutionException, InterruptedException {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "CompletableFuture";
            });
            System.out.println(future.get());
//        }
        return R.ok(future.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "CompletableFuture";
            });
            System.out.println(future.get() + i);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setBeanName(String name) {

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class TestData {
        private String username;
        private String phone;
    }
}
