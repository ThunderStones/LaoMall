package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_address")
public class UserAddress {
    @TableId(value = "address_id",type = IdType.AUTO)
    private Integer addressId;
    @TableField("user_id")
    private String userId;
    @TableField("phone")
    private String phone;
    @TableField("name")
    private String name;
    @TableField("area_id")
    private String areaId;
    @TableField("province")
    private String province;
    @TableField("city")
    private String city;
    @TableField("district")
    private String district;
    @TableField("is_default")
    private int isDefault;
    @TableField("detail")
    private String detail;
    @TableField("status")
    private String status;
}
