package com.resto.brand.web.dao;

import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.web.model.SmsLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SmsLogMapper  extends GenericDao<SmsLog,Long> {
    int deleteByPrimaryKey(Long id);

    int insert(SmsLog record);

    int insertSelective(SmsLog record);

    SmsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsLog record);

    int updateByPrimaryKey(SmsLog record);
    
    /**
     * 根据店铺ID查询短信记录
     * @param shopId
     * @return
     */
    List<SmsLog> selectListByShopId(@Param("shopId") Long shopId);

	List<SmsLog> selectListByShopIdAndDate(@Param("shopId") Long shopId, @Param("begin") Date begin);

    List<SmsLog> selectListByWhere(@Param("begin") String beginDate, @Param("end") String endDate);
    List<SmsLog> selectListByWhereBrandId(@Param("begin") String beginDate, @Param("end") String endDate, @Param("brandId") Long brandId);
	List<SmsLog> selectListByWhereBrandIdShopId(@Param("begin") String beginDate, @Param("end") String endDate, @Param("brandId") Long brandId, @Param("shopId") Long shopId);
	
	
	/**
	 * 根据品牌id查询
	 * @param brandId
	 * @return
	 */
	List<SmsLog> selectListByBrandId(Long brandId);

    SmsLog selectByMap(Map<String, Object> selectMap);
}
