package com.example.red.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.mapper.UserMapper;
import com.example.red.book.entity.User;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.model.vo.UserVo;
import com.example.red.book.security.util.JwtTokenUtil;
import com.example.red.book.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.user}")
    private String REDIS_KEY_USER;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    //密码需要客户端加密后传
    public UserVo login(String username, String password) {
        //获取缓存信息
        User user = getUserCache(username);
        if (user == null) {
            throw GlobalException.from(ResultCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_WRONG);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setToken(jwtTokenUtil.generateToken(user));
        return userVo;
    }

    @Override
    public User register(RegisterForm registerForm) {
        //查询是否有用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, registerForm.getUsername());
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_NOT_SAME);
        }
        User user = baseMapper.selectOne(wrapper);
        if (user != null) {
            throw GlobalException.from(ResultCode.USER_EXITS);
        }
        user = new User();
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(registerForm.getPassword());
        user.setUsername(registerForm.getUsername());
        user.setMobile(registerForm.getUsername());
        user.setPassword(encodePassword);
        user.setNickname("吃饱没事干");
        baseMapper.insert(user);
        return user;
    }

    public User getUserCache(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + username;
        User user = (User) redisService.get(key);
        if (user == null) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, username);
            user = baseMapper.selectOne(wrapper);
            redisService.set(key, user, REDIS_EXPIRE);
        }
        return user;
    }
}
