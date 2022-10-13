package com.example.red.book.admin.filter;

import com.example.red.book.admin.constant.SysUserConstant;
import com.example.red.book.admin.entity.LoginUser;
import com.example.red.book.admin.entity.SysUser;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.util.JwtTokenUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisService redisService;

    private static final String loginKey = SysUserConstant.LOGIN_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("进入JwtAuthenticationTokenFilter过滤器");
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //验证token
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        Long id = jwtTokenUtil.getIdFromToken(token);
        //从redis获取用户信息
        SysUser sysUser = (SysUser) redisService.get(loginKey + id);
        if (Objects.isNull(sysUser)) {
            throw GlobalException.from(ResultCode.USER_NOT_LOGIN);
        }
        LoginUser loginUser = new LoginUser(sysUser);
        //Todo 获取权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        //存入securityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
