package com.example.red.book.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.entity.User;
import com.example.red.book.manager.UserManager;
import com.example.red.book.mapper.UserMapper;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.model.form.UserUpdateForm;
import com.example.red.book.model.vo.UserVO;
import com.example.red.book.service.UserService;
import com.example.red.book.util.JwtTokenUtil;
import com.example.red.book.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserManager userManager;

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    //密码需要客户端加密后传
    public UserVO login(String username, String password) {
        //获取缓存信息
        User user = userManager.getUser(username);
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
    public Boolean updateUserById(UserUpdateForm userUpdateForm) {
        User user = new User();
        log.info("Text：{}", sessionUtil.getUserId());
        user.setId(sessionUtil.getUserId());
        user.setMobile(userUpdateForm.getMobile());
        user.setNickname(userUpdateForm.getNickname());
        user.setAvatar(userUpdateForm.getAvatar());
        //TODO-性别能否直接定义枚举？这样可以限制前端传的类型，包括在代码中使用也方便一点，唯一的坏处就是保存到数据库的时候需要转一下
        user.setSex(userUpdateForm.getSex());
        user.setCountry(userUpdateForm.getCountry());
        user.setCity(userUpdateForm.getCity());
        user.setDescription(userUpdateForm.getDescription());
        user.setCover(userUpdateForm.getCover());
        return this.updateById(user);
    }

}
