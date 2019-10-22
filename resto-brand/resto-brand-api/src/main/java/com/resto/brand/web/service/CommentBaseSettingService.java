package com.resto.brand.web.service;

import com.resto.brand.core.generic.GenericService;
import com.resto.brand.web.model.CommentBaseSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentBaseSettingService extends GenericService<CommentBaseSetting, String> {

   List<CommentBaseSetting> selectCommentBaseSetting();

   void createAndUpdate(CommentBaseSetting commentBaseSetting);
    
}
