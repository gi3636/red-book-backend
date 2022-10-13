package com.example.red.book.admin.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.example.red.book.admin.model.form.LoginForm;
import com.example.red.book.admin.model.vo.SysUserVO;
import com.example.red.book.admin.service.SysUserService;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.config.StsProperties;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.model.vo.StsToken;
import com.example.red.book.model.vo.UserVO;
import com.example.red.book.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */

@Api(tags = "管理后台授权模块")
@RestController
@RequestMapping("/admin/auth")
public class AuthAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StsProperties stsProperties;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public CommonResult<SysUserVO> login(@Validated @RequestBody LoginForm loginForm) {
        SysUserVO userVo = sysUserService.login(loginForm.getUsername(), loginForm.getPassword());
        return CommonResult.success(userVo);
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("logout")
    public CommonResult<SysUserVO> logout() {
        Boolean isSuccess = sysUserService.logout();
        return CommonResult.success(null);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<ObjectUtils.Null> register(@Validated @RequestBody RegisterForm registerForm) {
        Boolean registerSuccess = userService.register(registerForm);
        if (!registerSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取sts token")
    @GetMapping("sts/token")
    public CommonResult<StsToken> getStsToken() {
        // STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
        String endpoint = "sts.cn-hangzhou.aliyuncs.com";
        String accessKeyId = stsProperties.getKi();
        String accessKeySecret = stsProperties.getKs();
        // 填写步骤3获取的角色ARN。
        String roleArn = stsProperties.getRoleArn();
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "SessionTest";
        try {
            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
            String regionId = "";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            //request.setPolicy(policy); // 如果policy为空，则用户将获得该角色下所有权限。
            request.setDurationSeconds(3600L); // 设置临时访问凭证的有效时间为3600秒。
            final AssumeRoleResponse response = client.getAcsResponse(request);
            StsToken stsToken = new StsToken();
            stsToken.setAccessKeyId(response.getCredentials().getAccessKeyId());
            stsToken.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
            stsToken.setSecurityToken(response.getCredentials().getSecurityToken());
            stsToken.setExpiration(response.getCredentials().getExpiration());
            return CommonResult.success(stsToken);
        } catch (ClientException e) {
            throw GlobalException.from(ResultCode.GET_STS_TOKEN_ERROR);
        }
    }
}

