package com.resto.brand.web.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.resto.brand.core.generic.GenericDao;
import com.resto.brand.core.generic.GenericServiceImpl;
import com.resto.brand.core.util.ApplicationUtils;
import com.resto.brand.web.dao.CommentBaseSettingMapper;
import com.resto.brand.web.model.CommentBaseSetting;
import com.resto.brand.web.service.CommentBaseSettingService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Component
@Service
public class CommentBaseSettingServiceImpl extends GenericServiceImpl<CommentBaseSetting, String> implements CommentBaseSettingService {

    @Resource
    private CommentBaseSettingMapper commentbasesettingMapper;

    @Override
    public GenericDao<CommentBaseSetting, String> getDao() {
        return commentbasesettingMapper;
    }

    /**
    *@Description:查询评论设置
    *@Author:disvenk.dai
    *@Date:18:30 2018/5/24 0024
    */
    @Override
    public List<CommentBaseSetting> selectCommentBaseSetting() {
        List<CommentBaseSetting> commentBaseSettings = commentbasesettingMapper.selectList();
        return commentBaseSettings;
    }

    /**
    *@Description:新增和更改
    *@Author:disvenk.dai
    *@Date:19:00 2018/5/24 0024
    */
    @Override
    public void createAndUpdate(CommentBaseSetting commentBaseSetting) {
        List<CommentBaseSetting> commentBaseSettings = selectCommentBaseSetting();
        if(commentBaseSettings.size()==0) {
            commentBaseSetting.setId(ApplicationUtils.randomUUID());
            commentbasesettingMapper.insert(commentBaseSetting);
        }else {
            CommentBaseSetting commentBaseSetting1 = commentBaseSettings.get(0);
            commentBaseSetting.setId(commentBaseSetting1.getId());
            commentbasesettingMapper.updateByPrimaryKeySelective(commentBaseSetting);
        }
    }
}
