package com.resto.brand.web.dto;

import java.io.Serializable;

/**
 * Created by xielc on 2018/5/24.
 */
public class BrandDataDto implements Serializable{

    private static final long serialVersionUID = 1978665012894901538L;

    private String brandCount;

    private String shopCount;

    private String enableBrandCount;

    private String notEnableBrandCount;

    public String getBrandCount() {
        return brandCount;
    }

    public void setBrandCount(String brandCount) {
        this.brandCount = brandCount;
    }

    public String getShopCount() {
        return shopCount;
    }

    public void setShopCount(String shopCount) {
        this.shopCount = shopCount;
    }

    public String getEnableBrandCount() {
        return enableBrandCount;
    }

    public void setEnableBrandCount(String enableBrandCount) {
        this.enableBrandCount = enableBrandCount;
    }

    public String getNotEnableBrandCount() {
        return notEnableBrandCount;
    }

    public void setNotEnableBrandCount(String notEnableBrandCount) {
        this.notEnableBrandCount = notEnableBrandCount;
    }
}
