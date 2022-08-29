package com.example.red.book.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.red.book.api.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */
public interface UserMapper extends BaseMapper<User> {

    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
