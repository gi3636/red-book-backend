package com.example.red.book.admin.service;

import com.example.red.book.admin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.admin.model.vo.SysUserVO;
import com.example.red.book.model.vo.UserVO;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
public interface SysUserService extends IService<SysUser> {

    SysUserVO login(String username, String password);

    Boolean logout();
}
