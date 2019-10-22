package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.web.dao.BusinessFormatMapper;
import com.resto.brand.web.model.BusinessFormat;
import com.resto.brand.web.service.BusinessFormatService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 */
@Component
@Service
public class BusinessFormatServiceImpl extends GenericServiceImpl<BusinessFormat, Integer> implements BusinessFormatService {

    @Resource
    private BusinessFormatMapper businessformatMapper;

    @Override
    public GenericDao<BusinessFormat, Integer> getDao() {
        return businessformatMapper;
    } 

}
