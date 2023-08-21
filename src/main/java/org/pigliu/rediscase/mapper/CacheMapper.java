package org.pigliu.rediscase.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.Cache;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/19
 */
@Mapper
public interface CacheMapper extends Cache {
}
