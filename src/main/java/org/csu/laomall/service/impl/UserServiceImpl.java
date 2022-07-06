package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.laomall.entity.User;
import org.csu.laomall.persistence.UserMapper;
import org.csu.laomall.service.UserService;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final List<String> type = Arrays.asList("普通用户", "管理员");
    private final List<String> status = Arrays.asList("正常", "禁用", "删除");
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO register(String username, String password, String type) {
        if (checkUsernameExistence(username)) {
            return null;
        }
        User user = new User();
        user.setUserId(username);
        user.setPassword(password);
        user.setRegisterDate(new Date());
        if (this.type.contains(type)) {
            user.setType(type);
        }
        user.setStatus("正常");
        userMapper.insert(user);
        return new UserVO(userMapper.selectById(user.getUserId()));
    }

    @Override
    public boolean checkUsernameExistence(String username) {
        return userMapper.selectCount(new QueryWrapper<User>().eq("user_id", username)) > 0;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectById(username);
    }

    @Override
    public boolean login(String username, String password) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_id", username).eq("password", password)) != null;
    }

    @Override
    public UserVO getUserInfo(String username) {
        User user = userMapper.selectById(username);
        if (user == null) {
            return null;
        } else {
            return new UserVO(user);
        }
    }

    @Override
    public UserVO modifyUserInfo(UserVO originUserVO, User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        if (user.getPhone() != null) {
            userUpdateWrapper.set("phone", user.getPhone());
        }
        if (user.getNickName() != null) {
            userUpdateWrapper.set("nick_name", user.getNickName());
        }
        if (user.getBirthday() != null) {
            userUpdateWrapper.set("birthday", user.getBirthday());
        }
        if (user.getSex() != null) {
            userUpdateWrapper.set("sex", user.getSex());
        }
        if (user.getStatus() != null) {
            userUpdateWrapper.set("status", user.getStatus());
        }
        if (user.getEmail() != null) {
            userUpdateWrapper.set("email", user.getEmail());
        }
        if (user.getAddress() != null) {
            userUpdateWrapper.set("address", user.getAddress());
        }
        if (user.getPassword() != null) {
            userUpdateWrapper.set("password", user.getPassword());
        }
        userUpdateWrapper.eq("user_id", originUserVO.getUserId());
        userMapper.update(null, userUpdateWrapper);
        return new UserVO(userMapper.selectById(originUserVO.getUserId()));
    }

    @Override
    public List<UserVO> getAllUserInfo() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("type", "普通用户"));
        List<UserVO> userVOs = new ArrayList<>();
        for (User user : users) {
            userVOs.add(new UserVO(user));
        }
        return userVOs;
    }

    @Override
    public List<UserVO> getDeletedUserInfo() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("type", "普通用户").eq("status", "删除"));
        List<UserVO> userVOs = new ArrayList<>();
        for (User user : users) {
            userVOs.add(new UserVO(user));
        }
        return userVOs;
    }
}