package com.example.red.book.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.admin.constant.SysUserConstant;
import com.example.red.book.admin.entity.LoginUser;
import com.example.red.book.admin.entity.SysUser;
import com.example.red.book.admin.mapper.SysUserMapper;
import com.example.red.book.admin.model.vo.SysUserVO;
import com.example.red.book.admin.service.SysUserService;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisService redisService;

    private static final String loginKey = SysUserConstant.LOGIN_KEY;

    @Override
    public SysUserVO login(String username, String password) {
        //AuthenticationManager authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw GlobalException.from(ResultCode.LOGIN_FAILED);
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getSysUser().getId();
        String token = jwtTokenUtil.generateToken(userId, loginUser.getUsername());
        redisService.set(loginKey + userId, loginUser.getSysUser());
        SysUserVO sysUserVO = new SysUserVO(loginUser.getSysUser());
        sysUserVO.setToken(token);
        return sysUserVO;
    }

    @Override
    public Boolean logout() {
        //获取SecurityContextHolder中的用户信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getSysUser().getId();
        redisService.del(loginKey + id);
        //删除redis的值
        return true;
    }
}
