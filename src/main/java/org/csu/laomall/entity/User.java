package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;
    @TableField()
    private String password;
    @TableField
    private String phone;
    @TableField("nick_name")
    private String nickName;
    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    @TableField("register_date")
    private Date registerDate;
    @TableField
    private String sex;
    @TableField
    private String status;
    @TableField
    private String type;
    @TableField("email")
    private String email;
    @TableField("address")
    private String address;
}
