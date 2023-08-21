package org.pigliu.rediscase;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/19
 */
@SpringBootTest
public class mybatis {

    @Autowired
    private SqlSession sqlSession;
}
