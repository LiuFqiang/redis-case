package org.pigliu.rediscase.juc;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 查询月份表
 *
 * @author liufuqiang
 * @since 2023/8/3
 */
public interface BaseLogMapper<T> extends BaseMapper<T> {

	/**
	 * 分页查询
	 *
	 * @param page         分页条件
	 * @param queryWrapper 查询条件
	 * @param tableName    表名
	 * @return
	 */
	@Select("select * from ${tableName} ${ew.customSqlSegment}")
	IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper, @Param("tableName") String tableName);
}
