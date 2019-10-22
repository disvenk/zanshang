 package com.resto.brand.web.controller.business;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resto.brand.core.entity.Result;
import com.resto.brand.core.util.ApplicationUtils;
import com.resto.brand.web.controller.GenericController;
import com.resto.brand.web.model.BrandUser;
import com.resto.brand.web.service.BrandUserService;

@Controller
@RequestMapping("branduser")
public class BrandUserController extends GenericController{

	@Resource
	BrandUserService branduserService;
	
	@RequestMapping("/list")
    public void list(){
    }

	@RequestMapping("/list_all")
	@ResponseBody
	public List<BrandUser> listData(){
		return branduserService.selectList();
	}
	
	@RequestMapping("/list_one")
	@ResponseBody
	public Result list_one(String id){
		BrandUser branduser = branduserService.selectById(id);
		return getSuccessResult(branduser);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public Result create(@Valid BrandUser brandUser){
		List<BrandUser> brandUsers = branduserService.checkoutUsernameAndEmail(brandUser.getUsername(), brandUser.getEmail());
		if (brandUsers!=null && brandUsers.size()>0) {
			log.info(">>>>>>>>>>校验用户名或邮箱长度>>>>>>>>>>>>>"+brandUsers.size());
			for (BrandUser user : brandUsers) {
				if (StringUtils.equals(user.getUsername(),brandUser.getUsername())){
					return Result.getError("用户名已经存在");
				}else if (StringUtils.equals(user.getEmail(),brandUser.getEmail())){
					return Result.getError("邮箱已经存在");
				}
			}
		}
		branduserService.creatBrandUser(brandUser);
		return Result.getSuccess();
	}
	
	@RequestMapping("/modify")
	@ResponseBody
	public Result modify(@Valid BrandUser brandUser){
		//判断是否修改了密码
		if("".equals(brandUser.getPassword().trim()) || brandUser.getPassword().trim() == null){
			brandUser.setPassword(null);
		}else{
			brandUser.setPassword(ApplicationUtils.pwd(brandUser.getPassword()));
		}
		branduserService.update(brandUser);
		return Result.getSuccess();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String id){
		branduserService.delete(id);
		return Result.getSuccess();
	}
}
