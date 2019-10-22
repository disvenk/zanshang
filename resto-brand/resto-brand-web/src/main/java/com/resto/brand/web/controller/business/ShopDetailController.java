 package com.resto.brand.web.controller.business;

 import com.google.zxing.WriterException;
 import com.resto.brand.core.entity.PictureResult;
 import com.resto.brand.core.entity.Result;
 import com.resto.brand.core.util.*;
 import com.resto.brand.web.controller.GenericController;
 import com.resto.brand.web.model.ShopDetail;
 import com.resto.brand.web.model.WechatConfig;
 import com.resto.brand.web.service.ShopDetailService;
 import com.resto.brand.web.service.WechatConfigService;
 import org.apache.commons.lang3.time.DateFormatUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.multipart.MultipartFile;

 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.validation.Valid;
 import java.io.File;
 import java.io.IOException;
 import java.util.Date;
 import java.util.List;
 import java.util.UUID;

 @Controller
@RequestMapping("shopdetail")
public class ShopDetailController extends GenericController{

	@Resource
	ShopDetailService shopdetailService;

	@Autowired
	WechatConfigService wechatConfigService;
	
	@RequestMapping("/list")
    public void list(){
    }

	@RequestMapping("/list_all")
	@ResponseBody
	public List<ShopDetail> listData(){
	    List<ShopDetail> lists = shopdetailService.selectList();
	    System.out.println(lists);
		return lists;
	}
	
	@RequestMapping("list_one")
	@ResponseBody
	public Result list_one(Long id){
		ShopDetail shopdetail = shopdetailService.selectById(id);
		return getSuccessResult(shopdetail);
	}
	
	@RequestMapping("create")
	@ResponseBody
	public Result create(@Valid ShopDetail shopDetail){
		if(shopDetail.getBusinessFormatId()==null){
		   return Result.getError("业态不能为空");
		}
		shopdetailService.insertShopDetail(shopDetail);
		return Result.getSuccess();
	}
	
	@RequestMapping("modify")
	@ResponseBody
	public Result modify(@Valid ShopDetail shopDetail){
		if(shopDetail.getBusinessFormatId()==null){
		   return Result.getError("业态不能为空");
		}
		shopdetailService.update(shopDetail);
		return Result.getSuccess();
	}

	/**
	 * 假删除
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Result delete(Long id){
		shopdetailService.deleteBystate(id);
		return Result.getSuccess();
	}

	 @RequestMapping("uploadFile")
	 @ResponseBody
	 public String uploadFile(MultipartFile file, HttpServletRequest request){
		 String type = request.getParameter("type");
		 String systemPath = request.getServletContext().getRealPath("");
		 systemPath = systemPath.replaceAll("\\\\", "/");
		 int lastR = systemPath.lastIndexOf("/");
		 systemPath = systemPath.substring(0,lastR)+"/";
		 String filePath = "upload/files/"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		 File finalFile = FileUpload.fileUp(file, systemPath+filePath,UUID.randomUUID().toString(),type);
		 PictureResult picResult = UploadFilesUtil.uploadPic(finalFile);
		 return picResult.getUrl();
	 }

	 @RequestMapping("openQRCode")
	 @ResponseBody
	 public String openQRCode(Long shopId) throws IOException, WriterException {
		 ShopDetail shopDetail = shopdetailService.selectByPrimaryKey(shopId);
		 WechatConfig wechatConfig = wechatConfigService.selectByBrandId(shopDetail.getBrandId());
		 String token = WeChatUtils.getAccessToken(wechatConfig.getAppid(), wechatConfig.getAppsecret());
		 org.json.JSONObject qrParam = new org.json.JSONObject();
		 qrParam.put("QrCodeId", shopId);
		 String result = WeChatUtils.getParamQrCode(token, qrParam.toString());//二维码的附带参数字符串类型，长度不能超过64
		 org.json.JSONObject obj = new org.json.JSONObject(result);
		 String img = obj.has("ticket")?obj.getString("ticket"):"";
		 String conteng = WeChatUtils.showQrcode(img);
		 return conteng;
	 }
}
