package com.resto.brand.web.dao;

import com.resto.brand.web.model.BusinessFormat;
import com.resto.brand.core.generic.GenericDao;

public interface BusinessFormatMapper  extends GenericDao<BusinessFormat,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessFormat record);

    int insertSelective(BusinessFormat record);

    BusinessFormat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessFormat record);

    int updateByPrimaryKey(BusinessFormat record);
}
