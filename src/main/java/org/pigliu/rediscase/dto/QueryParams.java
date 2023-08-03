package org.pigliu.rediscase.dto;

import lombok.Data;

/**
 * 查询参数
 *
 * @author liufuqiang
 * @since 2023/8/3
 */
@Data
public class QueryParams {
	private String date;
	private String tableName;
	private String orderId;
	private String username;
	private String bookId;
	private String bookName;
	private String chapterId;
	private String chapterName;
	private String origin;
	private String type;

	private String startTime;
	private String endTime;

	private Integer pageNum;
	private Integer pageSize;
}
