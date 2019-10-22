package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.resto.brand.core.enums.SmsLogType;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.core.util.DateUtil;
import com.resto.brand.core.util.SMSUtils;
import com.resto.brand.web.dao.SmsLogMapper;
import com.resto.brand.web.dto.Names;
import com.resto.brand.web.model.SmsLog;
import com.resto.brand.web.service.BrandService;
import com.resto.brand.web.service.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
@Service
public class SmsLogServiceImpl extends GenericServiceImpl<SmsLog, Long> implements SmsLogService {

    @Resource
    private SmsLogMapper smslogMapper;

    @Autowired
	private BrandService brandService;

    
    @Override
    public GenericDao<SmsLog, Long> getDao() {
        return smslogMapper;
    }


	@Override
	public List<SmsLog> selectListByShopId(Long shopId) {
		
		return smslogMapper.selectListByShopId(shopId);
	}

	@Override
	public List<SmsLog> selectListByShopIdAndDate(Long ShopId) {
		Date begin = DateUtil.getDateBegin(DateUtil.getAfterDayDate(new Date(), -2));
		return smslogMapper.selectListByShopIdAndDate(ShopId,begin);
	}

	@Override
	public PageInfo<SmsLog> selectListWhere(String begin, String end, Integer start, Integer length) {
		if(("".equals(begin)||begin==null)&&(end==null||"".equals(""))){
			begin = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
			end = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
		}

        PageHelper.startPage(start,length);
		//查询短信记录
		List<SmsLog> list =  smslogMapper.selectListByWhere(begin, end);
		list.forEach(n->{
			Names names = brandService.getNames(n.getShopDetailId());
			n.setBrandName(names.getBrandName());
			n.setShopName(names.getShopName());
		});

		for (SmsLog smsLog : list) {
			smsLog.setSmsLogTyPeName(SmsLogType.getSmsLogTypeName(smsLog.getSmsType()));
		}
		PageInfo<SmsLog> pageInfo = new PageInfo<>(list);
		return pageInfo;

	}

	@Override
	public PageInfo<SmsLog> selectListWhere(String begin,String end, Long brandId, Integer start, Integer length) {
		if(("".equals(begin)||begin==null)&&(end==null||"".equals(""))){
			begin = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
			end = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
		}
        //if(brandId!=1l){
            PageHelper.startPage(start,length);
        //}

		//查询短信记录
		List<SmsLog> list =  smslogMapper.selectListByWhereBrandId(begin, end, brandId);
		list.forEach(n->{
			Names names = brandService.getNames(n.getShopDetailId());
			n.setBrandName(names.getBrandName());
			n.setShopName(names.getShopName());
		});
		for (SmsLog smsLog : list) {
			smsLog.setSmsLogTyPeName(SmsLogType.getSmsLogTypeName(smsLog.getSmsType()));
		}

        PageInfo<SmsLog> pageInfo = new PageInfo<>(list);
        return pageInfo;

	}

	@Override
	public PageInfo<SmsLog> selectListWhere(String begin,String end, Long brandId, Long shopId, Integer start, Integer length) {
        if(("".equals(begin)||begin==null)&&(end==null||"".equals(""))){
            begin = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
            end = DateUtil.formatDate(new Date(),"yyyy-MM-dd");
        }

        PageHelper.startPage(start,length);

		//查询短信记录
		List<SmsLog> list =  smslogMapper.selectListByWhereBrandIdShopId(begin, end, brandId, shopId);
		list.forEach(n->{
			Names names = brandService.getNames(n.getShopDetailId());
			n.setBrandName(names.getBrandName());
			n.setShopName(names.getShopName());
		});
		for (SmsLog smsLog : list) {
			smsLog.setSmsLogTyPeName(SmsLogType.getSmsLogTypeName(smsLog.getSmsType()));
		}

        PageInfo<SmsLog> pageInfo = new PageInfo<>(list);
        return pageInfo;

	}

	@Override
	public List<SmsLog> selecByBrandId(Long brandId) {
		List<SmsLog> list = smslogMapper.selectListByBrandId(brandId);
		for (SmsLog smsLog : list) {
			smsLog.setSmsLogTyPeName(SmsLogType.getSmsLogTypeName(smsLog.getSmsType()));
		}
		return list;
	}


    @Override
    public SmsLog selectByMap(Map<String, Object> selectMap) {
        return smslogMapper.selectByMap(selectMap);
    }

	@Override
	public JSONObject sendMessage(Long brandId, Long shopId, int smsType, String sign, String code_temp,String phone ,JSONObject jsonObject) {
		return SMSUtils.sendMessage(phone,jsonObject,sign,code_temp);
	}

//	@Override
//	public JSONObject sendMessage(String telephone, JSONObject sms, String sign, String code_temp,String brandId) {
//		return SMSUtils.sendMessage(telephone,sms,sign,code_temp);
//	}

}
