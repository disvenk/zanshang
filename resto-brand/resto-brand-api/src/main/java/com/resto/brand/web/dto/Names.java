package com.resto.brand.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by disvenk.dai on 2018-07-18 16:52
 */
@Data
public class Names implements Serializable{

    public String brandName;
    public String shopName;
    public Integer businessFormatId;
    public String formatsName;
}
