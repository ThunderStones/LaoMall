package org.csu.laomall.controller.controller;

import com.zhenzi.sms.ZhenziSmsClient;
import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.common.ResponseCode;
import org.csu.laomall.entity.User;
import org.csu.laomall.service.UserService;
import org.csu.laomall.util.JWTUtil;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("")
    @PassToken
    public CommonResponse<UserVO> register(@RequestBody User user) {
        if (user.getUserId().isEmpty() || user.getPassword().isEmpty() || user.getPhone().isEmpty()) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "用户名,密码或手机不能为空");
        }
        UserVO userVO = userService.register(user.getUserId(), user.getPassword(), user.getType(), user.getPhone());
        if (userVO == null) {
            return CommonResponse.createForError("注册失败，用户名已存在");
        }
        return CommonResponse.createForSuccess(userVO);

    }

    @PostMapping("/token")
    @PassToken
    public CommonResponse<String> login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "用户名或密码不能为空");
        }
        // check username and password
        if (!userService.login(username, password)) {
            return CommonResponse.createForError("登录失败，用户名或密码错误");
        }
        UserVO userVO = userService.getUserInfo(username);
        if (!userVO.getStatus().equals("正常")) {
            return CommonResponse.createForError("登录失败，用户被禁用或删除");
        }
        // get token
        String token = JWTUtil.createToken(username);
        return CommonResponse.createForSuccess("Bearer " + token);
    }
    @GetMapping("/existence/{userId}")
    @PassToken
    public CommonResponse<Map<String, Boolean>> checkUserIdExistence(@PathVariable String userId) {
        Map<String, Boolean> map = new HashMap<>(1);
        map.put("Existence", userService.checkUsernameExistence(userId));
        return CommonResponse.createForSuccess(map);
    }
    @GetMapping("")
    public CommonResponse<UserVO> getUserInfo(HttpServletRequest request) {
        UserVO userVO = (UserVO) request.getAttribute("user");
        if (userVO == null) {
            return CommonResponse.createForError("找不到该用户");
        }else {
            return CommonResponse.createForSuccess(userVO);
        }
    }

    @PutMapping()
    public CommonResponse<UserVO> modifyUserInfo(@RequestBody User user, HttpServletRequest request) {
        UserVO originUserVO = (UserVO) request.getAttribute("user");
        System.out.println(user);
        UserVO userVO = userService.modifyUserInfo(originUserVO, user);
        return CommonResponse.createForSuccess(userVO);
    }

    @GetMapping("/all")
    public CommonResponse<List<UserVO>> getAllUserInfo() {
//        UserVO userVO = (UserVO) request.getAttribute("user");
        return CommonResponse.createForSuccess(userService.getAllUserInfo());
    }
    @GetMapping("/deleted")
    public CommonResponse<List<UserVO>> getDeletedUserInfo() {
        return CommonResponse.createForSuccess(userService.getDeletedUserInfo());

    }
    @PutMapping("/admin/user")
    public CommonResponse<UserVO> adminModifyUserInfo(@RequestParam String userId, @RequestBody User user) {
        UserVO userVO = userService.getUserInfo(userId);
        if (userVO == null) {
            return CommonResponse.createForError("找不到该用户");
        }

        return CommonResponse.createForSuccess(userService.modifyUserInfo(userVO, user));
    }

    @GetMapping("/admin/{userId}")
    public CommonResponse<UserVO> adminGetUserInfo(@PathVariable String userId) {
        UserVO userVO = userService.getUserInfo(userId);
        if (userVO == null) {
            return CommonResponse.createForError("找不到该用户");
        }
        return CommonResponse.createForSuccess(userVO);
    }

    @GetMapping("/token/phone")
    @PassToken
    public CommonResponse<Map<String, String>> getTokenByValidPhone(@RequestParam String phone) {
        if (phone.length() != 11) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "手机号不能为空或格式错误");
        }
        UserVO userVO = userService.getUserInfoByPhone(phone);
        if (userVO == null) {
            return CommonResponse.createForError("找不到该用户");
        }
        String token = JWTUtil.createToken(userVO.getUserId());
        Map<String, String> map = new HashMap<>(1);
        map.put("token", "Bearer " + token);
        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "111420", "62436dea-5302-4385-bb42-b57bdcf15ac0");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", phone);
        params.put("templateId", "9884");
        String[] templateParams = new String[2];
        templateParams[0] = Integer.toString((int)(Math.random()*(9999-1000+1))+1000);
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);
        String result = null;
        try {
            result = client.send(params);
        } catch (Exception e) {
            return CommonResponse.createForError("发送失败");
        }
        System.out.println(result);
        if (!result.contains("发送成功")) {
            return CommonResponse.createForError("发送失败");
        }
        map.put("code", templateParams[0]);
        return CommonResponse.createForSuccess(map);
    }
}
