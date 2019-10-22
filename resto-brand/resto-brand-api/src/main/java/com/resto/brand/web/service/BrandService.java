package com.resto.brand.web.service;

import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.dto.BrandDataDto;
import com.resto.brand.web.dto.Names;
import com.resto.brand.web.model.Brand;

import java.util.List;

public interface BrandService extends GenericService<Brand, Long> {

	/**
	 * 根据BrandId 查询品牌信息
	 * @param brandId
	 * @return
	 */
	Brand selectByPrimaryKey(Long brandId);

	/**
	 * 查询所有状态为1的品牌详细信息，包含该品牌的数据库配置和微信配置
	 * @return
	 */
	List<Brand> selectBrandDetailInfo();

	/**
	 * 添加 品牌 信息（包括关联的表）
	 * @param brand
	 * @return
	 */
	void insertInfo(Brand brand);

	/**
	 * 修改 品牌 信息（包括关联的表）
	 * @param brand
	 */
	void updateInfo(Brand brand);

	/**
	 * 删除信息
	 * @param brandId
	 */
	void deleteInfo(Long brandId);

	/**
	 * 查询 品牌 信息 和 该品牌旗下的店铺
	 * @return
	 */
	List<Brand> queryBrandAndShop();


	/**
	 * 验证品牌的标识是否重复
	 * @param brandId
	 * @param brandSign
	 * @return
	 */
	boolean validataBrandInfo(String brandSign,Long brandId);

	/**
	 * 验证品牌的标识是否重复
	 * @param brandSign
	 * @return
	 */
	List<Brand> selectByBrandSign(String brandSign);

	/**
	 * 品牌数量是否启用统计
	 * @return
	 */
	List<BrandDataDto> listBrandDataDto();

	Names getNames(Long shopId);
}
