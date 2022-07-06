package org.csu.laomall.controller.controller;

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
        if (user.getUserId().isEmpty() || user.getPassword().isEmpty()) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "用户名或密码不能为空");
        }
        UserVO userVO = userService.register(user.getUserId(), user.getPassword(), user.getType());
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
}
