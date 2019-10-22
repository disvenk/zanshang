 package com.resto.brand.web.controller.business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.resto.brand.core.entity.JSONResult;
import com.resto.brand.core.util.ApplicationUtils;
import com.resto.brand.web.model.ShopDetail;
import com.resto.brand.web.service.RankingsService;
import com.resto.brand.web.service.ShopDetailService;
import com.resto.brand.web.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resto.brand.web.controller.GenericController;
import com.resto.brand.core.entity.Result;
import com.resto.brand.web.model.CommentBaseSetting;
import com.resto.brand.web.service.CommentBaseSettingService;

@Controller
@RequestMapping("commentbasesetting")
public class CommentBaseSettingController extends GenericController{

	@Resource
	CommentBaseSettingService commentbasesettingService;

	@Resource
	ShopDetailService shopDetailService;

	@Resource
	RankingsService rankingsService;
	
	@RequestMapping("/list")
    public void list(){
    }


    /**
    *@Description:查询单个评论设置
    *@Author:disvenk.dai
    *@Date:18:34 2018/5/24 0024
    */
    @RequestMapping("selectCommentBaseSetting")
	@ResponseBody
	public Result selectCommentBaseSetting(){
		List<CommentBaseSetting> commentBaseSettings = commentbasesettingService.selectCommentBaseSetting();
		if(commentBaseSettings.size()==0 || commentBaseSettings==null){
			return new JSONResult(null,"暂未设置参数，请输入设置参数并保存",false);
		}
		return new JSONResult(commentBaseSettings.get(0),"查询成功",true);
	}

	@RequestMapping("createAndUpdate")
	@ResponseBody
	public Result create(@RequestBody CommentBaseSetting commentBaseSetting){
		if(commentBaseSetting.getCommentCount()==null){
            return Result.getError("店铺每日至少获取评论数不能为空");
		}
		if(commentBaseSetting.getDistanceTime()==null){
            return Result.getError("用户评论间隔时间不能为空");
		}
		commentbasesettingService.createAndUpdate(commentBaseSetting);
		//查询出所有店铺，修改缓存里的加入总榜条件
		List<ShopDetail> shopDetailList = shopDetailService.selectList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String key = "ZSTOTALAPPRAISE".concat(LocalDate.now().format(formatter));
		for (ShopDetail shopDetail : shopDetailList){
			if (RedisUtil.exists(shopDetail.getId().toString().concat(key))){
				JSONObject totalAppraise = JSON.parseObject(RedisUtil.get(shopDetail.getId().toString().concat(key)).toString());
				totalAppraise.put("commentCount", commentBaseSetting.getCommentCount());
				RedisUtil.set(shopDetail.getId().toString().concat(key), totalAppraise.toJSONString());
			}
		}
		//删除不满足当前条件的总榜记录信息
		rankingsService.deleteRankings(commentBaseSetting.getCommentCount(), LocalDate.now().format(formatter));
		return Result.getSuccess();
	}

}
