package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carousel")
public class Carousel {
    @TableId(value = "carousel_id", type = IdType.AUTO)
    Integer carouselId;
    @TableField("carousel_name")
    String carouselName;
    @TableField("img_url")
    String imgUrl;
    @TableField("redirect_url")
    String linkUrl;
    @TableField("carousel_rank")
    int carouselRank;
    @TableField("is_deleted")
    int isDeleted;

}
