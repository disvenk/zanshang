package com.resto.brand.web.dao;

import java.util.List;

import com.resto.brand.web.dto.BrandDataDto;
import com.resto.brand.web.dto.Names;
import org.apache.ibatis.annotations.Param;

import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.web.model.Brand;

import javax.naming.Name;

public interface BrandMapper  extends GenericDao<Brand,Long> {
    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    /**
     * 查询 品牌状态为1的品牌详细信息，包含该品牌的数据库配置和微信配置
     * @return
     */
    List<Brand> selectBrandDetailInfo();

    //查询 品牌 ID 和 Name
    List<Brand> selectIdAndName();

	Brand selectBySign(String brandSign);

	/**
	 * 验证参数是否重复
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	int validataParam(@Param("paramName") String paramName,@Param("paramValue")String paramValue);


	/**
	 * 给指定品牌充值短信数量
	 * @param brandId
	 * @param number
	 * @return
	 */
	int chargeSms(@Param("brandId")Long brandId,@Param("number")int number);

    /**
     * 查询标识是否存在
     * @param brandSign
     * @return
     */
    List<Brand> selectByBrandSign(@Param("brandSign")String brandSign);

    /**
     * 品牌数量是否启用统计
     * @return
     */
    List<BrandDataDto> listBrandDataDto();

	Names selectBrandNameAndShopName(@Param("shopId")Long shopId);
}
