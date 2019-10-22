package com.resto.brand.web.model;

import java.io.Serializable;
import java.util.Date;

public class SmsLog implements Serializable {

    private Long id;

    private String phone;

    private String content;

    private Date createTime;

    private Integer smsType;

    private String smsResult;

    private Long shopDetailId;

    private Long brandId;

    private Boolean isSuccess;

    private String brandName;

    private String shopName;
    /**
     * 短信验证码类型名字
     */
    private String smsLogTyPeName;



    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSmsLogTyPeName() {
		return smsLogTyPeName;
	}

	public void setSmsLogTyPeName(String smsLogTyPeName) {
		this.smsLogTyPeName = smsLogTyPeName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getSmsResult() {
        return smsResult;
    }

    public void setSmsResult(String smsResult) {
        this.smsResult = smsResult == null ? null : smsResult.trim();
    }

    public Long getShopDetailId() {
        return shopDetailId;
    }

    public void setShopDetailId(Long shopDetailId) {
        this.shopDetailId = shopDetailId == null ? null : shopDetailId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId == null ? null : brandId;
    }

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}