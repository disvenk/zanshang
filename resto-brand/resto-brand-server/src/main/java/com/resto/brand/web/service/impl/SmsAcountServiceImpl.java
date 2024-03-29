package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.web.dao.SmsAcountMapper;
import com.resto.brand.web.model.SmsAcount;
import com.resto.brand.web.service.SmsAcountService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 *
 */
@Component
@Service
public class SmsAcountServiceImpl extends GenericServiceImpl<SmsAcount, String> implements SmsAcountService {
	

	@Resource
	private SmsAcountMapper smsAcountMapper;

	
	@Override
	public GenericDao<SmsAcount, String> getDao() {
		return smsAcountMapper;
	}

	@Override
	public SmsAcount selectByBrandId(Long brandId) {
		return smsAcountMapper.selectByBrandId(brandId);
	}

	@Override
	public void updateByBrandId(Long brandId) {
		smsAcountMapper.updateByBrandId(brandId);
	}

	@Override
	public int chargeSms(Long brandId, int number,BigDecimal money) {
		return smsAcountMapper.chargeSms(brandId, number,money);
	}

	@Override
	public BigDecimal selectSmsUnitPriceByBrandId(Long brandId) {
		return smsAcountMapper.selectSmsUnitPriceByBrandId(brandId);
	}

	@Override
	public BigDecimal selectInvoiceMoney(Long brandId) {
		return smsAcountMapper.selectInvoiceMoney(brandId);
	}


}
