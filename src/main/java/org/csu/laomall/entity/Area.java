package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("area")
public class Area {
    @TableId(value = "area_id",type = IdType.INPUT)
    private String areaId;
    @TableField("area_name")
    private String name;
    @TableField("parent_id")
    private String parentId;
    @TableField()
    private int level;

}
