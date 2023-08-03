package org.pigliu.rediscase.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pigliu.rediscase.dto.QueryParams;
import org.pigliu.rediscase.juc.BaseLogMapper;
import org.springframework.stereotype.Service;

/**
 * 查询日志流水
 * 继承此类可实现分页查询流水表功能
 * M 是 mapper 对象，T 是实体，P是公用查询参数
 *
 * @author liufuqiang
 * @since 2023/8/3
 */
public class BaseLogService<M extends BaseLogMapper<T>, T, P extends QueryParams> extends ServiceImpl<M, T> {

	/**
	 * 分页查询
	 *
	 * @param tableName 月份表
	 * @param page      分页
	 * @return
	 */
	public IPage<T> selectPage(String tableName, IPage<T> page) {
		return this.selectPage(tableName, page, new QueryWrapper<>());
	}

	/**
	 * 分页查询
	 *
	 * @param tableName    月份表
	 * @param page         分页
	 * @param queryWrapper 查询条件
	 * @return
	 */
	public IPage<T> selectPage(String tableName, IPage<T> page, Wrapper<T> queryWrapper) {
		return this.baseMapper.selectPage(page, queryWrapper, tableName);
	}

	/**
	 * 分页查询
	 *
	 * @param params  查询对象
	 * @return
	 */
	public IPage<T> selectPage(P params) {
		String tableName = params.getTableName() + "_" + params.getDate();
		Page<T> page = new Page<>(params.getPageNum(), params.getPageSize());
		return this.selectPage(tableName, page, params);
	}

	/**
	 * 分页查询
	 *
	 * @param tableName    月份表
	 * @param page         分页
	 * @param params       查询对象
	 * @return
	 */
	public IPage<T> selectPage(String tableName, IPage<T> page, P params) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<>();
		if (StrUtil.isNotBlank(params.getBookId())) {
			queryWrapper.eq(true, "book_id", params.getBookId());
		}
		if (StrUtil.isNotBlank(params.getUsername())) {
			queryWrapper.eq(true, "username", params.getUsername());
		}
		if (StrUtil.isNotBlank(params.getOrderId())) {
			queryWrapper.eq(true, "order_id", params.getOrderId());
		}
		if (StrUtil.isNotBlank(params.getBookName())) {
			queryWrapper.like(true, "book_name", params.getBookName());
		}
		if (StrUtil.isNotBlank(params.getChapterId())) {
			queryWrapper.eq(true, "chapter_id", params.getChapterId());
		}
		if (StrUtil.isNotBlank(params.getChapterName())) {
			queryWrapper.eq(true, "chapter_name", params.getChapterName());
		}
		if (StrUtil.isNotBlank(params.getOrigin())) {
			queryWrapper.in(true, "origin", StrUtil.split(params.getOrigin(), ","));
		}
		if (StrUtil.isNotBlank(params.getType())) {
			queryWrapper.in(true, "type", StrUtil.split(params.getType(), ","));
		}
		if (StrUtil.isNotBlank(params.getStartTime())) {
			queryWrapper.ge(true, "create_time", params.getStartTime());
		}
		if (StrUtil.isNotBlank(params.getEndTime())) {
			queryWrapper.le(true, "create_time", params.getEndTime());
		}
		queryWrapper.orderByDesc("create_time");

		return super.baseMapper.selectPage(page, queryWrapper, tableName);
	}

	/**
	 * mybatis3.4.3 bug
	 * @return
	 */
	@Override
	protected Class<T> currentMapperClass() {
		return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
	}

	@Override
	protected Class<T> currentModelClass() {
		return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
	}

}
