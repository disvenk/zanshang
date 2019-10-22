 package com.resto.brand.web.controller.business;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import com.resto.brand.web.model.ShopDetail;
import com.resto.brand.web.service.ShopDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.resto.brand.web.controller.GenericController;
import com.resto.brand.core.entity.Result;
import com.resto.brand.web.model.BusinessFormat;
import com.resto.brand.web.service.BusinessFormatService;

@Controller
@RequestMapping("businessformat")
public class BusinessFormatController extends GenericController{

	@Resource
	private BusinessFormatService businessformatService;

	@Resource
	private ShopDetailService shopdetailService;
	
	@RequestMapping("/list")
    public void list(){
    }

	@RequestMapping("/list_all")
	@ResponseBody
	public List<BusinessFormat> listData(){
		return businessformatService.selectList();
	}
	
	@RequestMapping("list_one")
	@ResponseBody
	public Result list_one(Integer id){
		BusinessFormat businessformat = businessformatService.selectById(id);
		return getSuccessResult(businessformat);
	}
	
	@RequestMapping("create")
	@ResponseBody
	public Result create(@Valid BusinessFormat brand){
		businessformatService.insert(brand);
		return Result.getSuccess();
	}
	
	@RequestMapping("modify")
	@ResponseBody
	public Result modify(@Valid BusinessFormat brand){
		businessformatService.update(brand);
		return Result.getSuccess();
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Result delete(Integer id){
		BusinessFormat  businessformat=businessformatService.selectById(id);
		Integer count = shopdetailService.selectByBusinessFormatId(businessformat.getId());
		if(count!=0){
			return new Result("该业态已被店铺选择，请先删除对应店铺",false);
		}
		businessformatService.delete(id);
		return Result.getSuccess();
	}
}
