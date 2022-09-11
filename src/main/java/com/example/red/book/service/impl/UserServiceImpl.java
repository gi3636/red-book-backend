package com.example.red.book.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.entity.User;
import com.example.red.book.manager.UserManager;
import com.example.red.book.mapper.UserMapper;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.model.vo.UserVO;
import com.example.red.book.util.JwtTokenUtil;
import com.example.red.book.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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




    @Override
    //密码需要客户端加密后传
    public UserVO login(String username, String password) {
        //获取缓存信息
        User user = userManager.getUserByCache(username);
        if (user == null) {
            throw GlobalException.from(ResultCode.USER_NOT_FOUND);
        }
        if (!DigestUtil.md5Hex(password).equals(user.getPassword())) {
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
        User existUser = userManager.getAndSetCacheByUsername(registerForm.getUsername());
        if (existUser != null) {
            throw GlobalException.from(ResultCode.USER_EXITS);
        }

        User user = new User();
        //将密码进行加密操作
        String encodePassword = DigestUtil.md5Hex(registerForm.getPassword());
        user.setUsername(registerForm.getUsername());
        user.setMobile(registerForm.getUsername());
        user.setPassword(encodePassword);
        user.setAvatar("https://avatars1.githubusercontent.com/u/" + (int) (Math.random() * 1000));
        user.setNickname("吃饱没事干");
        int result = baseMapper.insert(user);
        return result > 0;
    }

    @Override
    public Boolean updateUserById(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        boolean isSuccess = this.updateById(user);
        user = this.getById(user.getId());
        if (isSuccess) {
            //更新缓存
            userManager.updateUserCache(user);
        }
        return isSuccess;
    }

}
