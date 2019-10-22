package com.resto.brand.web.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.web.dao.ShopDetailMapper;
import com.resto.brand.web.model.ShopDetail;
import com.resto.brand.web.service.BrandService;
import com.resto.brand.web.service.ShopDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Component
@Service
public class ShopDetailServiceImpl extends GenericServiceImpl<ShopDetail, Long> implements ShopDetailService {

    @Resource
    private ShopDetailMapper shopdetailMapper;

    @Resource
    private BrandService brandService;

    @Override
    public GenericDao<ShopDetail, Long> getDao() {
        return shopdetailMapper;
    }

    @Value("${BIGDATA_URL}")
    private String BIGDATA_URL;
    @Value("${BIGDATA_NAME}")
    private String BIGDATA_NAME;
    @Value("${BIGDATA_PASSWORD}")
    private String BIGDATA_PASSWORD;
    @Value("${BIGDATA_DRIVER}")
    private String BIGDATA_DRIVER;
	
	 @Override
    /**
     * 添加门店的详细信息
     */
    public int insertShopDetail(ShopDetail shopDetail) {

     //生成当前时间赋值给添加的时间
     Date date = new Date();
     shopDetail.setAddTime(date);

     //赋值添加人
     shopDetail.setAddUser("-1");
     //设置更新的人
     shopDetail.setUpdateUser("-1");

     //设置状态为1
     shopDetail.setState(1);

     //设置店铺的是否开启
     shopDetail.setIsOpen(true);

     int shopCount = shopdetailMapper.insertSelective(shopDetail);
       return  shopCount;
    }

	@Override
	public int deleteBystate(Long id) {
		ShopDetail shopDetail = shopdetailMapper.selectByPrimaryKey(id);
		if(shopDetail!=null){
			shopDetail.setState(0);
			
		}
		return shopdetailMapper.updateByPrimaryKeySelective(shopDetail);
		
	}

	@Override
	public List<ShopDetail> selectByBrandId(Long brandId) {
		return shopdetailMapper.selectByBrandId(brandId);
	}

    @Override
    public ShopDetail selectByPrimaryKey(Long id) {
        return shopdetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShopDetail> selectByShopCity(Long brandId) {
        return shopdetailMapper.selectByShopCity(brandId);
    }

    @Override
    public List<ShopDetail> selectByCityOrName(String name, Long brandId) {
        return shopdetailMapper.selectByCityOrName(name, brandId);
    }

    @Override
    public List<ShopDetail> selectByCity(String city, Long brandId) {
        return shopdetailMapper.selectByCity(city, brandId);
    }

    @Override
    public ShopDetail selectByRestaurantId(Integer restaurantId) {
        return shopdetailMapper.selectByRestaurantId(restaurantId);
    }

    @Override
    public List<ShopDetail> selectOrderByIndex(Long brandId) {
        return shopdetailMapper.selectOrderByIndex(brandId);
    }

    @Override
    public Integer selectByBusinessFormatId(Integer businessFormatId) {
        return shopdetailMapper.selectByBusinessFormatId(businessFormatId);
    }
}
