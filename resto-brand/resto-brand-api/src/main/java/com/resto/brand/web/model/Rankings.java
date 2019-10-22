package com.resto.brand.web.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Rankings implements Serializable {
    private String id;

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

    private Integer foatingNumber;

    private Boolean type;

    private Date createTime;

    private Integer appraiseCount;
}