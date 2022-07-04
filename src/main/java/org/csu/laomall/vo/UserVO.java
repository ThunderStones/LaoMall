package org.csu.laomall.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
