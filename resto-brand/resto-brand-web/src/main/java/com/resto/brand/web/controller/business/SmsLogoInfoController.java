package com.resto.brand.web.controller.business;

import com.github.pagehelper.PageInfo;
import com.resto.brand.web.controller.GenericController;
import com.resto.brand.web.model.Brand;
import com.resto.brand.web.model.ShopDetail;
import com.resto.brand.web.model.SmsLog;
import com.resto.brand.web.service.BrandService;
import com.resto.brand.web.service.ShopDetailService;
import com.resto.brand.web.service.SmsAcountService;
import com.resto.brand.web.service.SmsLogService;
import com.resto.brand.web.util.DataTablesOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("smsloginfo")
public class SmsLogoInfoController extends GenericController {
	
	@Resource
    ShopDetailService shopDetailService;
	
	@Resource
	SmsLogService smsLogService;

	@Autowired
	BrandService brandService;
	
	@Resource
    SmsAcountService smsAcountService;

	@RequestMapping("brand/list")
    public void listBrand(){
    }

	@RequestMapping("shop/list")
	public void listShop(){
	}

	@ResponseBody
	@RequestMapping("brandList")
	public List<Brand> brandList(){
		return brandService.selectList();
	}

	@ResponseBody
	@RequestMapping("shopList")
	public List<ShopDetail> shopList(){
		return shopDetailService.selectList();
	}
	
	/**
	 * 查询店铺的名字
	 */
	@ResponseBody
	@RequestMapping("/shopName")
	public List<ShopDetail> queryList(Long brandId){
		return shopDetailService.selectByBrandId(brandId);
	}
	
	@ResponseBody
	@RequestMapping("/list_all")
	public List<SmsLog> list_all(){
		return smsLogService.selecByBrandId(getCurrentBrandId());
	}
	
	/**
	 * 根据时间和页面传过来的店铺id查询短信
	 * @param begin
	 * @param end
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByShopsAndDate")
	public DataTablesOutput<SmsLog> listByWhere(@RequestParam("begin")String begin,
												@RequestParam("end")String end,
												@RequestParam("brandId")Long brandId,
												@RequestParam("shopId")Long shopId,
												@RequestParam("draw")Integer draw,
												@RequestParam("start")Integer start,
												@RequestParam("length")Integer length){



		DataTablesOutput<SmsLog> dtable=new DataTablesOutput<SmsLog>();
		dtable.setDraw(draw);
		if(brandId == 0 && shopId == 0){
			PageInfo<SmsLog> pageInfo = smsLogService.selectListWhere(begin, end, start, length);
			dtable.setRecordsTotal(pageInfo.getTotal());
			dtable.setRecordsFiltered(pageInfo.getTotal());
			dtable.setData(pageInfo.getList());
			return dtable;
		}else if(shopId == 0){
			PageInfo<SmsLog> pageInfo = smsLogService.selectListWhere(begin, end, brandId, start, length);
			dtable.setRecordsTotal(pageInfo.getTotal());
			dtable.setRecordsFiltered(pageInfo.getTotal());
			dtable.setData(pageInfo.getList());
			return dtable;
		}else if(shopId != 0){
			PageInfo<SmsLog> pageInfo = smsLogService.selectListWhere(begin, end, brandId, shopId, start, length);
			dtable.setRecordsTotal(pageInfo.getTotal());
			dtable.setRecordsFiltered(pageInfo.getTotal());
			dtable.setData(pageInfo.getList());
			return dtable;
		}else{
			return dtable;
		}
	}
	
	/**
	 * 根据时间和当前店铺id查询短信
	 * @param begin
	 * @param end
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByShopAndDate")
	public List<SmsLog> listByWhere(@RequestParam("begin")String begin,@RequestParam("end")String end){
		PageInfo<SmsLog> pageInfo = smsLogService.selectListWhere(begin, end, (long) 1, null, null);
		return pageInfo.getList();
	}
	
	@ResponseBody
	@RequestMapping("/listByShopId")
	public List<SmsLog> listByShop(@RequestParam("shopId")Long shopId){
		return smsLogService.selectListByShopId(shopId) ;
	}
	
	
	@ResponseBody
	@RequestMapping("/querySmsNum")
	public String querySmsNumByBrand(){
		return smsAcountService.selectSmsUnitPriceByBrandId(getCurrentBrandId()).toString();
	}
	

}
