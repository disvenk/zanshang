package com.resto.brand.web.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.resto.brand.core.entity.Result;
import com.resto.brand.core.util.QRCodeUtil;
import com.resto.brand.core.util.StringUtils;
import com.resto.brand.core.util.WeChatUtils;
import com.resto.brand.web.controller.GenericController;
import com.resto.brand.web.dto.BrandDataDto;
import com.resto.brand.web.model.Brand;
import com.resto.brand.web.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("brand")
public class BrandController extends GenericController {

    @Resource
    BrandService brandService;

    @RequestMapping("/list")
    public void list() {
    }

    @RequestMapping("/list_all")
    @ResponseBody
    public List<Brand> listData() {
        List<Brand> brands = brandService.selectBrandDetailInfo();
        return brands;
    }

    @RequestMapping("/brand_data")
    @ResponseBody
    public List<BrandDataDto> listBrandDataDto() {
        List<BrandDataDto> list = brandService.listBrandDataDto();
        return list;
    }

    @RequestMapping("/list_without_self")
    @ResponseBody
    public Result list_without_self() {
        List<Brand> lists = brandService.selectBrandDetailInfo();
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getId().equals(getCurrentBrandId())) {
                lists.remove(i);
            }
        }
        return getSuccessResult(lists);
    }


    /**
     * 此方法用于 在添加商家 用户时，查询所需要的参数包括（品牌信息，店铺信息）
     *
     * @return
     */
    @RequestMapping("/queryBrandAndShop")
    @ResponseBody
    public List<Brand> queryBrandAndShop() {
        return brandService.queryBrandAndShop();
    }

    @RequestMapping("list_one")
    @ResponseBody
    public Result list_one(Long id) {
        Brand brand = brandService.selectById(id);
        return getSuccessResult(brand);
    }

    @RequestMapping("create")
    @ResponseBody
    public Result create(Brand brand) {
        List<Brand> list=brandService.selectByBrandSign(brand.getBrandSign());
        if(isContainChinese(brand.getBrandSign())){
            return Result.getError("标识不能包含中文");
        }
        if(list!=null && !list.isEmpty()){
            return Result.getError("标识已存在");
        }
        //设置设置当前操作的用户
        brand.setAddUser(getCurrentBrandId().toString());
        brand.setUpdateUser(getCurrentBrandId().toString());
        brandService.insertInfo(brand);
        return Result.getSuccess();
    }

    @RequestMapping("modify")
    @ResponseBody
    public Result modify(@Valid Brand brand) {
        List<Brand> list=brandService.selectByBrandSign(brand.getBrandSign());
        if(isContainChinese(brand.getBrandSign())){
            return Result.getError("标识不能包含中文");
        }
        if(list!=null && !list.isEmpty()){
            return Result.getError("标识已存在");
        }
        //修改人 ID
        brand.setUpdateUser(getCurrentBrandId().toString());
        brandService.updateInfo(brand);
        return Result.getSuccess();
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @RequestMapping("/savePhoneList")
    @ResponseBody
    public Result savePhoneList(Long brandId,String phoneList) {
        //修改人 ID
        Brand brand = brandService.selectById(brandId);
        brand.setPhoneList(phoneList);
        brandService.update(brand);
        return Result.getSuccess();
    }

    @RequestMapping("/saveWhitePhoneList")
    @ResponseBody
    public Result saveWhitePhoneList(Long brandId,String phoneList) {
        //修改人 ID
        Brand brand = brandService.selectById(brandId);
        brand.setWhitePhoneList(phoneList);
        brandService.update(brand);
        return Result.getSuccess();
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(Long id) {
        brandService.deleteInfo(id);
        return Result.getSuccess();
    }

    @RequestMapping("validateInfo")
    @ResponseBody
    public Result validateInfo(String brandSign, Long brandId) {
        boolean flag = brandService.validataBrandInfo(brandSign, brandId);
        return new Result(flag);
    }

    @RequestMapping("getWxMpQrCode")
    @ResponseBody
    public void getWxMpQrCode(HttpServletResponse response,HttpServletRequest request,String appId,String secret) throws IOException {
        //验证
        if(StringUtils.isEmpty(appId) || StringUtils.isEmpty(secret)){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<h1>参数错误~</h1>");
            return;
        }
        //获取Token
        String token = WeChatUtils.getAccessToken(appId,secret);
        String qrParam = "-";
        if(token.indexOf("errcode")>0 || token.indexOf("errmsg")>0){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(token);
            return;
        }
        //获取 qrCode
        JSONObject obj = JSONObject.parseObject(WeChatUtils.getParamQrCode(token,qrParam));
        if(!obj.containsKey("url")){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(obj.toJSONString());
            return;
        }
        //生成图片
        FileInputStream fis = null;
        File file = null;
        response.setContentType("image/gif");
        String fileName = System.currentTimeMillis()+"";
        try {
            QRCodeUtil.createQRCode(obj.getString("url"), getFilePath(request, null), fileName);
            OutputStream out = response.getOutputStream();
            file = new File(getFilePath(request, fileName));
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.gc();//手动回收垃圾，清空文件占用情况，解决无法删除文件
            file.delete();
        }
    }
}
