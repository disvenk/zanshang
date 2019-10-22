package com.resto.brand.web.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopDetail implements Serializable {

    private static final long serialVersionUID = 3344561212215601151L;

    private Long id;

    @NotBlank(message="店铺名字不能为空")
    private String name;

    private String address;

    private String longitude;

    private String latitude;

    private String phone;

    @DateTimeFormat(pattern="HH:mm")
    private Date openTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date closeTime;

    private Integer status;

    private String remark;

    private String addUser;

    private Date addTime;

    private Date updateTime;

    private Long brandId;

    private Integer state;

    private String updateUser;

    private String city;

    private String photo;

    private String appid;

    private String appsecret;

    private String brandName;

    private Boolean isOpen;

    private Integer appraiseCD;

    private Integer shopDetailIndex;

    private Integer businessFormatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean open) {
        isOpen = open;
    }

    public Integer getShopDetailIndex() {
        return shopDetailIndex;
    }

    public void setShopDetailIndex(Integer shopDetailIndex) {
        this.shopDetailIndex = shopDetailIndex;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getAppraiseCD() {
        return appraiseCD;
    }

    public void setAppraiseCD(Integer appraiseCD) {
        this.appraiseCD = appraiseCD;
    }

    public Integer getBusinessFormatId() {
        return businessFormatId;
    }

    public void setBusinessFormatId(Integer businessFormatId) {
        this.businessFormatId = businessFormatId;
    }
}