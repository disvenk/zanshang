package com.resto.brand.web.service;

import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.model.ShowTagBrand;


public interface ShowTagService extends GenericService<ShowTagBrand,Object>   {

        ShowTagBrand selectShowTagByTitle(String title);
}
