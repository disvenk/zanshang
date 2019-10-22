package com.resto.brand.web.dao;

import java.util.List;

import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.web.model.ShopDetail;
import org.apache.ibatis.annotations.Param;

public interface ShopDetailMapper  extends GenericDao<ShopDetail,Long> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ShopDetail record);

    ShopDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopDetail record);

    int updateByPrimaryKey(ShopDetail record);
    
    /**
     * 根据品牌ID 查询店铺的id和名称
     * @param brandId
     * @return
     */
    List<ShopDetail> selectIdAndName(Long brandId);

    /**
     * 根据品牌ID查询所有的店铺
     * @param brandId
     * @return
     */
	List<ShopDetail> selectByBrandId(Long brandId);

    /**
     * 查询当前品牌下所有店铺所在的所有地址
     * @param brandId
     * @return
     */
    List<ShopDetail> selectByShopCity(Long brandId);

    /**
     * 条件查询城市、店铺名满足需求的所有店铺
     * @param name
     * @return
     */
    List<ShopDetail> selectByCityOrName(@Param("name") String name,@Param("brandId") Long brandId);

    List<ShopDetail> selectByCity(@Param("city") String city,@Param("brandId") Long brandId);

    ShopDetail selectByRestaurantId(Integer restaurantId);

    List<ShopDetail> selectOrderByIndex(Long brandId);

    Integer selectByBusinessFormatId(@Param("businessFormatId")Integer businessFormatId);
}
