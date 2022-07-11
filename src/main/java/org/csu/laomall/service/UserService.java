package org.csu.laomall.service;

import org.csu.laomall.entity.User;
import org.csu.laomall.vo.UserVO;

import java.util.List;

public interface UserService {
    UserVO register(String username, String password, String type, String phone);

    boolean checkUsernameExistence(String username);

    User getUserByUsername(String username);

    boolean login(String username, String password);

    UserVO getUserInfo (String username);

    UserVO modifyUserInfo(UserVO originUserVO, User user);

    List<UserVO> getAllUserInfo();

    List<UserVO> getDeletedUserInfo();
}
