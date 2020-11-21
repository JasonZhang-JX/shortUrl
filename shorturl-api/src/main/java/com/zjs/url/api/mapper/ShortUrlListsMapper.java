package com.zjs.url.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjs.url.api.entity.ShortUrlLists;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 群二维码短链生成表 Mapper 接口
 * </p>
 *
 * @author zjs
 * @since 2020-10-29
 */
@Mapper
public interface ShortUrlListsMapper extends BaseMapper<ShortUrlLists> {

}
