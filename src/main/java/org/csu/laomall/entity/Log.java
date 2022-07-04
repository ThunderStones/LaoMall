package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("logs")
public class Log {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;
    @TableField("user_id")
    private String userId;
    @TableField("create_date")
    private Date logDate;
    @TableField("product_id")
    private int productId;
}
