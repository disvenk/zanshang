package com.resto.brand.web.service;

import java.util.List;
import java.util.Map;

import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.model.SmsChargeOrder;


public interface SmsChargeOrderService extends GenericService<SmsChargeOrder, String> {
	/**
	 * 根据品牌ID 查询短信充值订单
	 * @param brandId
	 * @return
	 */
	List<SmsChargeOrder> selectByBrandId(Long brandId);
	
	/**
	 * 保存充值订单
	 * @param brandId
	 * @param total_fee
	 * @return
	 */
	public SmsChargeOrder saveSmsOrder(Long brandId, String total_fee);
	
	/**
	 * 保存通过银行卡转账的短信充值订单
	 * @param brandId
	 * @param serialNumber
	 * @return
	 */
	public boolean saveSmsOrderByBank(Long brandId, String serialNumber);
	
	/**
	 * 支付宝异步通知时，检查订单信息和完善充值操作
	 * @return 
	 * 	返回 true，则表示订单匹配，完成充值订单，
	 * 	返回false，则表示订单金额不匹配，不进行充值操作
	
	 * @return
	 */
	public boolean checkSmsChargeOrder_AliPay(Map<String, String> resultMap);
	
	/**
	 * 微信异步通知时，检查订单信息和完善充值操作
	 * @return 
	 * 	返回 true，则表示订单匹配，完成充值订单，
	 * 	返回false，则表示订单金额不匹配，不进行充值操作
	 */
	public boolean checkSmsChargeOrder_WxPay(Map<String, String> resultMap);

	List<SmsChargeOrder> selectOtherPay();

	/**
	 * 确认银行卡充值的订单
	 */
	void paySuccess(SmsChargeOrder smsChargeOrder, Long brandId);

	List<SmsChargeOrder> selectListData();
	
	/**
	 * 当支付验证失败时，保存第三方支付返回的所有参数
	 * @param resultMap	第三方返回的参数
	 * @param payType	支付方式
	 */
	void saveResultParam(Map<String, String> resultMap, String payType);
}
