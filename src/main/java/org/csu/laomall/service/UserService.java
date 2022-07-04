package org.csu.laomall.service;

import org.csu.laomall.entity.User;
import org.csu.laomall.vo.UserVO;

public interface UserService {
    UserVO register(String username, String password, String type);

    boolean checkUsernameExistence(String username);

    User getUserByUsername(String username);

    boolean login(String username, String password);

    UserVO getUserInfo (String username);

    UserVO modifyUserInfo(UserVO originUserVO, User user);
}
