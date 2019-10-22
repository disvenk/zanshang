package com.resto.brand.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by disvenk.dai on 2018-06-22 14:54
 */

@Data
public class RankingsDto implements Serializable {

    private String id;

    private String appraiseId;

    private Long brandId;

    private Long shopDetailId;

    private String brandName;

    private String shopName;

    private String formats;

    private BigDecimal totalScore;

    private BigDecimal serviceScore;

    private BigDecimal environmentalScore;

    private BigDecimal costPerformanceScore;

    private BigDecimal atmosphereScore;

    private BigDecimal produceScore;

    private Integer orderGrade;

    private Integer status;

    private Integer foatingNumber;

    private Boolean type;

    private Date createTime;

    private Long times;

    private Integer appraiseCount;

    private Long count;

    private Integer formatsId;
}
