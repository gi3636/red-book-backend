package com.example.red.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.RedisConstant;
import com.example.red.book.entity.User;
import com.example.red.book.manager.UserManager;
import com.example.red.book.mapper.UserMapper;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.model.vo.UserVO;
import com.example.red.book.security.util.JwtTokenUtil;
import com.example.red.book.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserManager userManager;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    //密码需要客户端加密后传
    public UserVO login(String username, String password) {
        //获取缓存信息
        User user = userManager.getUserByCache(username);
        if (user == null) {
            throw GlobalException.from(ResultCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_WRONG);
        }
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(user, userVo);
        userVo.setToken(jwtTokenUtil.generateToken(user));
        return userVo;
    }

    @Override
    public Boolean register(RegisterForm registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_NOT_SAME);
        }

        //查询是否有用户
        User existUser = userManager.getByUsername(registerForm.getUsername());
        if (existUser != null) {
            throw GlobalException.from(ResultCode.USER_EXITS);
        }

        User user = new User();
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(registerForm.getPassword());
        user.setUsername(registerForm.getUsername());
        user.setMobile(registerForm.getUsername());
        user.setPassword(encodePassword);
        user.setAvatar("https://avatars1.githubusercontent.com/u/" + (int) (Math.random() * 1000));
        user.setNickname("吃饱没事干");
        int result = baseMapper.insert(user);
        return result > 0;
    }

}
