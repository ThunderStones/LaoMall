package org.csu.laomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsVO {
    long accessCount;
    long newUserCount;
    long productCount;
    long orderCount;
}
