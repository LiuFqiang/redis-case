package org.pigliu.rediscase;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.pigliu.rediscase.service.DrawService;
import org.pigliu.rediscase.service.DynamicSelectService;
import org.pigliu.rediscase.service.RedisPipleService;
import org.pigliu.rediscase.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisCaseApplicationTests {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DrawService drawService;

    @Autowired
    private RedisPipleService pipleService;

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private DynamicSelectService dynamicSelectService;
//
//    @Test
//    void contextLoads () {
//        transactionService.test();
//    }

    @Test
    void drawImg() {
        drawService.createTask();
        //10319367
        // 8e523ce05c378cfd110afabd9d131305
    }

    @Test
    void queryTask() {
        drawService.queryTask();
    }

    @Test
    void paddingImg() {
        drawService.paddingImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/68197b9ea840a0d1ed7c2ef538599bce30",
                "霸道总裁之出狱归来卖火柴的小女孩", "木马不是马");

    }

    @Test
    void drawTest() {
        drawService.drawTest();
    }


    @Test
    void execute() {
        pipleService.execute();
    }


    @Test
    void generateEncryptor() {
        String password = "udC0&4^&#fez5";
        System.out.println(stringEncryptor.encrypt(password));
    }

    @Test
    void dynamicQuery() {
        dynamicSelectService.queryList();
    }
}
