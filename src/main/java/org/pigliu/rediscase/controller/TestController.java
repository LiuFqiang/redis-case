package org.pigliu.rediscase.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.pigliu.rediscase.annotation.Signature;
import org.pigliu.rediscase.dto.R;
import org.pigliu.rediscase.service.DynamicSelectService;
import org.pigliu.rediscase.service.ThreadLocalService;
import org.pigliu.rediscase.service.ThreadPoolService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
@Validated
@RequiredArgsConstructor
public class TestController {

    private final DynamicSelectService dynamicSelectService;

    private final ThreadPoolService threadPoolService;

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
    public Object testPar(@NotNull final Integer a) {
        return R.ok();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class TestData {
        private String username;
        private String phone;
    }
}
