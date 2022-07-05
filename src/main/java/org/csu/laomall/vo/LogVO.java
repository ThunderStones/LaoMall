package org.csu.laomall.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.laomall.entity.Log;
import org.csu.laomall.entity.Product;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogVO {
    private Integer logId;
    private String userId;
    private Date logDate;
    private int productId;
    private Product product;

    public LogVO(Log log) {
        if (log.getUserId() == null) {
            this.userId = "未登录";
        } else {
            this.userId = log.getUserId();
        }
        this.logId = log.getLogId();
        this.logDate = log.getLogDate();
        this.productId = log.getProductId();
    }
}
