package com.resto.brand.web.dao;

import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.web.model.ShowTagBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShowTagBrandMapper extends GenericDao<ShowTagBrand,Object> {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowTagBrand record);

    int insertSelective(ShowTagBrand record);

    ShowTagBrand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShowTagBrand record);

    int updateByPrimaryKey(ShowTagBrand record);

    public List<ShowTagBrand> selectList();

    ShowTagBrand selectShowTagByTitle(@Param("title") String title);

}