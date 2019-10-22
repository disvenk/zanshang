package com.resto.brand.web.controller.business;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resto.brand.web.controller.GenericController;
import com.resto.brand.core.entity.Result;
import com.resto.brand.web.model.ShowTagBrand;
import com.resto.brand.web.service.ShowTagService;

@Controller
@RequestMapping("showtag")
public class ShowTagController extends GenericController{

	@Resource
	ShowTagService showTagService;
	
	@RequestMapping("/list")
    public void list(){
    }

	@RequestMapping("/list_all")
	@ResponseBody
	public List<ShowTagBrand> listData(){
		return showTagService.selectList();
	}
	
	@RequestMapping("list_one")
	@ResponseBody
	public Result list_one(Integer id){
		ShowTagBrand showphoto = showTagService.selectById(id);
		return getSuccessResult(showphoto);
	}

	@RequestMapping("create")
	@ResponseBody
	public Result create(ShowTagBrand brand){
		ShowTagBrand showTagBrand = showTagService.selectShowTagByTitle(brand.getTitle());
		if (showTagBrand!=null){
			if (StringUtils.equals(showTagBrand.getTitle(),brand.getTitle())){
				return Result.getError("标签已经存在");
			}else {
				showTagService.insert(brand);
				return Result.getSuccess();
			}
		}else {
			showTagService.insert(brand);
			return Result.getSuccess();
		}

	}

	@RequestMapping("modify")
	@ResponseBody
	public Result modify(@Valid ShowTagBrand brand){
		showTagService.update(brand);
		return Result.getSuccess();
	}
}
