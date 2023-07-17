package org.pigliu.rediscase.service.strategy;

import lombok.Getter;

/**
 * 策略实现消息类型
 *
 * @author liufuqiang
 * @since 2023/7/17
 */
@Getter
public enum MessageType {
    WECHAT,

    FEISHU,

    MAIL_163,

    MAIL_QQ;
}
