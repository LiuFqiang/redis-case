package org.pigliu.rediscase;

import org.junit.jupiter.api.Test;
import org.pigliu.rediscase.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisCaseApplicationTests {

    @Autowired
    private TransactionService transactionService;

    @Test
    void contextLoads () {
        transactionService.test();
    }

}
