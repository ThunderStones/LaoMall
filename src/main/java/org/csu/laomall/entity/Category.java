package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("category")
public class Category {
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;
    @TableField("name")
    private String name;
    @TableField("parent_id")
    private int parentId;
    @TableField("level")
    private int level;
    @TableField("description")
    private String description;
}
