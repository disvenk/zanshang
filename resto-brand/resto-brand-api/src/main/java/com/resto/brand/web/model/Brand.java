package com.resto.brand.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Brand implements Serializable{
    private Long id;
	
	@NotBlank(message="品牌名称不能为空")
    private String brandName;
	
	@NotBlank(message="品牌标志不能为空")
    private String brandSign;

    private Date createTime;

    private String wechatConfigId;

    private String databaseConfigId;

    private String brandUserId;
    
    private int smsCount;//品牌的剩余短信条数
    
    private BigDecimal smsUnitPrice;//短信单价
	
	private String addUser;
	
	private String updateUser;
	
	private Integer state;

	private String wechatImgUrl;

	//服务器IP地址
	private String serverIp;

	//外键实体
	private WechatConfig wechatConfig;
	private DatabaseConfig databaseConfig;
	private List<ShopDetail> shopDetail;
	
	private String brandSettingId;

	private Integer useState;

	private String phoneList;

	private String whitePhoneList;

	private Integer mqId;

	private String contractId;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Integer getMqId() {
		return mqId;
	}

	public void setMqId(Integer mqId) {
		this.mqId = mqId;
	}

	public String getWhitePhoneList() {
		return whitePhoneList;
	}

	public void setWhitePhoneList(String whitePhoneList) {
		this.whitePhoneList = whitePhoneList;
	}

	public String getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(String phoneList) {
		this.phoneList = phoneList;
	}

	public Integer getUseState() {
		return useState;
	}

	public void setUseState(Integer useState) {
		this.useState = useState;
	}

	public String getBrandSettingId() {
		return brandSettingId;
	}

	public void setBrandSettingId(String brandSettingId) {
		this.brandSettingId = brandSettingId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandSign() {
        return brandSign;
    }

    public void setBrandSign(String brandSign) {
        this.brandSign = brandSign == null ? null : brandSign.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWechatConfigId() {
        return wechatConfigId;
    }

    public void setWechatConfigId(String wechatConfigId) {
        this.wechatConfigId = wechatConfigId == null ? null : wechatConfigId.trim();
    }

    public String getDatabaseConfigId() {
        return databaseConfigId;
    }

    public void setDatabaseConfigId(String databaseConfigId) {
        this.databaseConfigId = databaseConfigId == null ? null : databaseConfigId.trim();
    }
    
	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	public BigDecimal getSmsUnitPrice() {
		return smsUnitPrice;
	}

	public void setSmsUnitPrice(BigDecimal smsUnitPrice) {
		this.smsUnitPrice = smsUnitPrice;
	}

	public String getBrandUserId() {
        return brandUserId;
    }

    public void setBrandUserId(String brandUserId) {
        this.brandUserId = brandUserId == null ? null : brandUserId.trim();
    }
    
    public Integer getState() {
		return (state == null || "".equals(state)) ? 1 : state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}

	public void setWechatConfig(WechatConfig wechatConfig) {
		this.wechatConfig = wechatConfig;
	}

	public DatabaseConfig getDatabaseConfig() {
		return databaseConfig;
	}

	public void setDatabaseConfig(DatabaseConfig databaseConfig) {
		this.databaseConfig = databaseConfig;
	}

	public List<ShopDetail> getShopDetail() {
		return shopDetail;
	}

	public void setShopDetail(List<ShopDetail> shopDetail) {
		this.shopDetail = shopDetail;
	}

	public String getWechatImgUrl() {
		return wechatImgUrl;
	}

	public void setWechatImgUrl(String wechatImgUrl) {
		this.wechatImgUrl = wechatImgUrl;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
}