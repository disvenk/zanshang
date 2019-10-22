package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.web.dao.ShowTagBrandMapper;
import com.resto.brand.web.model.ShowTagBrand;
import com.resto.brand.web.service.ShowTagService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 */
@Component
@Service
public class ShowTagServiceImpl extends GenericServiceImpl<ShowTagBrand, Object> implements ShowTagService {

    @Resource
    private ShowTagBrandMapper showTagBrandMapper;


    @Override
    public GenericDao<ShowTagBrand, Object> getDao() {
        return showTagBrandMapper;
    }


    @Override
    public ShowTagBrand selectShowTagByTitle(String title) {

        return showTagBrandMapper.selectShowTagByTitle(title);
    }
}
