package org.pigliu.rediscase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.Map;

@Mapper
public interface DynamicMapper {
    @Select("select * from ${tableName} limit 20")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(Map.class)
    void queryUserDB(@Param("column") String column,
                     @Param("tableName") String tableName,
                     @Param("condition") String condition,
                     @Param("limit") String limit,
                     ResultHandler<Map<String, Object>> handler);

    @Update("update b_book set author_name = '111111' where name = '1111';")
    int updateRows();
}
