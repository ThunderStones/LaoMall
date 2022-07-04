package org.csu.laomall.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.laomall.entity.User;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String userId;
    private String phone;
    private String nickName;
    private Date birthday;
    private Date registerDate;
    private String sex;
    private String status;
    private String type;

    public UserVO(User user) {
        userId = user.getUserId();
        phone = user.getPhone();
        nickName = user.getNickName();
        birthday = user.getBirthday();
        registerDate = user.getRegisterDate();
        sex = user.getSex();
        status = user.getStatus();
        type = user.getType();
    }
}
