package com.resto.brand.web.service;

import java.util.List;

import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.model.ShopDetail;

public interface ShopDetailService extends GenericService<ShopDetail, Long> {
	int insertShopDetail(ShopDetail shopDetail);

	int deleteBystate(Long id);

	List<ShopDetail> selectByBrandId(Long id);

	ShopDetail selectByPrimaryKey(Long id);

	List<ShopDetail> selectByShopCity(Long brandId);

	List<ShopDetail> selectByCityOrName(String name, Long brandId);

	List<ShopDetail> selectByCity(String city, Long brandId);

	ShopDetail selectByRestaurantId(Integer restaurantId);

	List<ShopDetail> selectOrderByIndex(Long brandId);

	Integer selectByBusinessFormatId(Integer businessFormatId);
}

