package com.resto.brand.web.controller.business;

import com.resto.brand.core.entity.Result;
import com.resto.brand.web.controller.GenericController;
import com.resto.brand.web.model.SmsAcount;
import com.resto.brand.web.service.SmsAcountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 短信账户 Controller
 * @author lmx
 *
 */
@Controller
@RequestMapping("smsacount")
public class SmsAcountController extends GenericController {
	
	@Resource
    SmsAcountService smsAcountService;
	
	@RequestMapping("/selectSmsAcount")
	@ResponseBody
	public Result selectSmsAcount(){
		SmsAcount smsAcount = smsAcountService.selectByBrandId(getCurrentBrandId());
		return getSuccessResult(smsAcount);
	}
	
}
