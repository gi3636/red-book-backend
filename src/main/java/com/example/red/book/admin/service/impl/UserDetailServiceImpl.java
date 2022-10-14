package com.example.red.book.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.red.book.admin.entity.LoginUser;
import com.example.red.book.admin.entity.SysUser;
import com.example.red.book.admin.mapper.SysUserMapper;
import com.example.red.book.admin.service.SysUserService;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser user = sysUserMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            throw GlobalException.from(ResultCode.USER_NOT_FOUND);
        }
        //Todo 查询对用用户权限
        List<String> permissions = new ArrayList<>(Arrays.asList("test", "admin"));


        return new LoginUser(user, permissions);
    }
}
