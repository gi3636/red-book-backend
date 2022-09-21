package com.example.red.book.service.impl;

import com.example.red.book.model.enums.SexEnum;


import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.entity.User;
import com.example.red.book.manager.UserManager;
import com.example.red.book.mapper.UserMapper;
import com.example.red.book.model.param.RegisterParam;
import com.example.red.book.model.param.UserUpdateParam;
import com.example.red.book.model.vo.UserVO;
import com.example.red.book.service.UserService;
import com.example.red.book.util.JwtTokenUtil;
import com.example.red.book.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
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
        userVo.setUsername(user.getUsername());
        userVo.setMobile(user.getMobile());
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        userVo.setSex(SexEnum.getByIndex((user.getSex())));
        userVo.setBirthday(user.getBirthday());
        userVo.setCountry(user.getCountry());
        userVo.setCity(user.getCity());
        userVo.setDescription(user.getDescription());
        userVo.setCover(user.getCover());
        userVo.setToken(jwtTokenUtil.generateToken(user));
        return userVo;
    }

    @Override
    public Boolean register(RegisterParam registerParam) {
        if (!registerParam.getPassword().equals(registerParam.getConfirmPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_NOT_SAME);
        }

        //查询是否有用户
        User existUser = userManager.getAndSetCacheByUsername(registerParam.getUsername());
        if (existUser != null) {
            throw GlobalException.from(ResultCode.USER_EXITS);
        }

        User user = new User();
        //将密码进行加密操作
        String encodePassword = DigestUtil.md5Hex(registerParam.getPassword());
        user.setUsername(registerParam.getUsername());
        user.setMobile(registerParam.getUsername());
        user.setPassword(encodePassword);
        user.setAvatar("https://avatars1.githubusercontent.com/u/" + (int) (Math.random() * 1000));
        user.setNickname("吃饱没事干");
        int result = baseMapper.insert(user);
        return result > 0;
    }

    @Override
    public Boolean updateUserById(UserUpdateParam userUpdateParam) {
        User user = new User();
        user.setId(sessionUtil.getUserId());
        user.setMobile(userUpdateParam.getMobile());
        user.setNickname(userUpdateParam.getNickname());
        user.setAvatar(userUpdateParam.getAvatar());
        //TODO-性别能否直接定义枚举？这样可以限制前端传的类型，包括在代码中使用也方便一点，唯一的坏处就是保存到数据库的时候需要转一下
        if (userUpdateParam.getSex() != null) {
            user.setSex(userUpdateParam.getSex().getIndex());
        }
        user.setCountry(userUpdateParam.getCountry());
        user.setCity(userUpdateParam.getCity());
        user.setDescription(userUpdateParam.getDescription());
        user.setCover(userUpdateParam.getCover());
        return this.updateById(user);
    }

}
