package com.resto.brand.web.service;

import java.math.BigDecimal;

import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.model.SmsAcount;

public interface SmsAcountService extends GenericService<SmsAcount, String> {
	
	/**
	 * 品牌查询短信账户的详情
	 * @param brandId
	 * @return
	 */
	SmsAcount selectByBrandId(Long brandId);

	/**
	 * 更新短信账户短信条数
	 * @param brandId
	 */
	void updateByBrandId(Long brandId);
	
	
	/**
	 * 给指定品牌充值短信数量
	 * @param brandId
	 * @param number
	 * @return
	 */
	int chargeSms(Long brandId, int number, BigDecimal money);
	
	BigDecimal selectSmsUnitPriceByBrandId(Long brandId);
	
	BigDecimal selectInvoiceMoney(Long brandId);
	
}
