package org.pigliu.rediscase.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AI画图
 * @author liufuqiang
 */
@Configuration
@ConfigurationProperties(prefix = "ai-draw")
@Data
public class AIDrawConfig {
    private String apiKey;
    private String secretKey;
}
