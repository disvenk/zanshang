package com.resto.brand.web.dao;

import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.web.model.BusinessFormat;
import com.resto.brand.web.model.CommentBaseSetting;

public interface CommentBaseSettingMapper  extends GenericDao<CommentBaseSetting,String> {
    int deleteByPrimaryKey(String id);

    int insert(CommentBaseSetting record);

    int insertSelective(CommentBaseSetting record);

    CommentBaseSetting selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentBaseSetting record);

    int updateByPrimaryKey(CommentBaseSetting record);

}