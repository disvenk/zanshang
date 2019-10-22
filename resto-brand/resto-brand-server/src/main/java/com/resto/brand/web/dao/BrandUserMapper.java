package com.resto.brand.web.dao;

import org.apache.ibatis.annotations.Param;
import com.resto.brand.web.model.BrandUser;

import java.util.List;

import com.resto.brand.core.generic.GenericDao;

public interface BrandUserMapper  extends GenericDao<BrandUser,String> {
    int deleteByPrimaryKey(String id);

    int insert(BrandUser record);

    int insertSelective(BrandUser record);

    BrandUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BrandUser record);

    int updateByPrimaryKey(BrandUser record);

	BrandUser selectByUsername(String username);

	BrandUser authentication(BrandUser branduser);
	/**
	 * 修改当前用户密码
	 * @param id
	 * @param password
	 */
	void updatePwd(@Param("id") String id,@Param("password")String password);

    void updateSuperPwd(@Param("id") String id,@Param("password")String password);

	List<BrandUser> selectListBybrandId(Long shopId);

	BrandUser selectOneByBrandId(Long brandId);

    BrandUser selectUserInfoByBrandIdAndRole(@Param("brandId") Long brandId, @Param("roleId") int roleId);

	BrandUser loginByWaitModel(@Param("username") String userName,@Param("password") String password);


	/**
	 * 根据 店铺ID 查询  品牌下所有的管理员信息
	 * Pos2.0 数据拉取接口			By___lmx
	 * @param shopId
	 * @return
	 */
	List<BrandUser> selectByShopId(@Param("shopId") Long shopId);

    BrandUser selectByPhone(@Param("phone") String phone);

	/**
	 * 校验邮箱和用户名
	 * @param username
	 * @param email
	 * @return
	 */
	List<BrandUser>  checkoutUsernameAndEmail(@Param("username")String username,@Param("email")String email);
}
