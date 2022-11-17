package org.pigliu.rediscase.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Data
public class RedisConfig {

    @Value("${redis.case.host}")
    private String host;
    @Value("${redis.case.port}")
    private Integer port;
    @Value("${redis.case.timeout}")
    private Integer timeout;
    @Value("${redis.case.pool.min-idle}")
    private Integer minIdle;
    @Value("${redis.case.pool.max-idle}")
    private Integer maxIdle;
    @Value("${redis.case.pool.max-active}")
    private Integer maxActive;
    @Value("${redis.case.pool.max-wait}")
    private Integer maxWaitMillis;

    @Bean(name = "caseJedisPoolConfig")
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }


    @Bean(name="caseRedisTemplate")
    public StringRedisTemplate caseRedisTemplate(JedisPoolConfig caseJedisPoolConfig){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(caseJedisPoolConfig);
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.afterPropertiesSet();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        return stringRedisTemplate;
    }
}
