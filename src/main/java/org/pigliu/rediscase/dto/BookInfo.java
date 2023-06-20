package org.pigliu.rediscase.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/6/13
 */
@Builder
@ToString
public class BookInfo {

    @DateTimeFormat
    private LocalDateTime localDateTime;

    private String bookId;
    @Singular
    private List<Integer> levels;

    public static void main(String[] args) {
        BookInfo info = BookInfo.builder()
                .bookId("1111111")
                .level(1)
                .build();
        System.out.println(info);
    }
}
