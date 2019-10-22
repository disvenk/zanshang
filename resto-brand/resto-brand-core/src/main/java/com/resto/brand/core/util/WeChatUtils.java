package com.resto.brand.core.util;

import com.alibaba.fastjson.JSON;
import com.resto.brand.core.entity.PictureResult;
import com.resto.brand.core.util.HttpRequest.HttpRequestException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.resto.brand.core.util.HttpClient.doPostAnsc;

public class WeChatUtils {
    static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    static final String GET_USER_INFO_URL_SUBSCRIBE = "https://api.weixin.qq.com/cgi-bin/user/info";//新的获取用户信息链接
    static final String USER_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    static final String GET_JS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    static final String GET_WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token";
    static final String WEIXIN_PAY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单接口
    static final String WEIXIN_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    static final String WEIXIN_REFRESH_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    static final String WEIXIN_CHECK_TOKEN_URL = "https://api.weixin.qq.com/sns/auth";
    static final String SEND_CUSTOM_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token";
    static final String GET_PARAM_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    static final String GET_FILE = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=";
    static final String GET_API_SET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=";
    static final String GET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=";
    static final String GET_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";
    static final String SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    static final String DEL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=";
    static final String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
//    static final String ACCESS_CACHE_SERVER = "http://42.96.203.79:9999";

    static final String ACCESS_CACHE_SERVER = "http://106.14.96.154:9999";
    //static final String ACCESS_CACHE_SERVER = "http://139.196.106.198:9999";

    static final String TOKEN = "resto_plus_wechat_token";

    public static String getUserAuthorizeUrl(String state, String redirectUrl, String appid) {
        StringBuffer url = new StringBuffer(USER_AUTHORIZE_URL + "?");
        url.append("appid=" + appid);
        try {
            url.append("&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8"));
            url.append("&response_type=code");
            url.append("&scope=snsapi_userinfo");
            url.append("&state=" + state);
            url.append("#wechat_redirect");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url.toString();
    }

    public static String getUrlAccessToken(String code, String appid, String secret) {
        StringBuffer url = new StringBuffer(GET_WEB_ACCESS_TOKEN_URL + "?");
        url.append("appid=" + appid);
        url.append("&secret=" + secret);
        url.append("&code=" + code);
        url.append("&grant_type=authorization_code");
        return HttpRequest.get(url.toString()).body();
    }

    public static String getUrlRefreshToken(String appid, String refresh_token) {
        StringBuffer url = new StringBuffer(WEIXIN_REFRESH_URL + "?");
        url.append("appid=" + appid);
        url.append("&grant_type=refresh_token");
        url.append("&refresh_token="+refresh_token);
        return HttpRequest.get(url.toString()).body();
    }

    public static String getURLCheckToken(String access_token,String openid){
        StringBuffer url = new StringBuffer(WEIXIN_CHECK_TOKEN_URL + "?");
        url.append("access_token=" + access_token);
        url.append("&openid=" + openid);
        return HttpRequest.get(url.toString()).body();
    }

    public static String getUserInfo(String token, String openid) {
        StringBuffer url = new StringBuffer(GET_USER_INFO_URL + "?");
        url.append("access_token=" + token);
        url.append("&openid=" + openid);
        url.append("&lang=zh_CN");
        return HttpRequest.get(url.toString()).body();
    }

    public static String getUserInfoSubscribe(String token, String openid) {
        StringBuffer url = new StringBuffer(GET_USER_INFO_URL_SUBSCRIBE + "?");
        url.append("access_token=" + token);
        url.append("&openid=" + openid);
        url.append("&lang=zh_CN");
        return HttpRequest.get(url.toString()).body();
    }

    public static String getJsAccessToken(String appid, String secret) {
        String url = ACCESS_CACHE_SERVER + "/jstoken/getstr/" + appid + "/" + secret;
        return HttpRequest.get(url).body();
    }

    public static String getAccessToken(String appid, String secret) {
        String url = ACCESS_CACHE_SERVER + "/token/getstr/" + appid + "/" + secret;
        return HttpRequest.get(url).body();
    }

    public static void flushAccessToken(String appid, String secret) {
        String url = ACCESS_CACHE_SERVER + "/token/flush/" + appid + "/" + secret;
        HttpRequest.get(url).body();
    }

    public static String sendCustomerMsg(String message, String openid, String appid, String secret) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("touser", openid);
        data.put("msgtype", "text");
        Map<String, String> content = new HashMap<String, String>();
        content.put("content", message);
        data.put("text", content);
        String accessToken = getAccessToken(appid, secret);
        String url = SEND_CUSTOM_MESSAGE_URL + "=" + accessToken;
        String jsonData = JSON.toJSONString(data);
        String result = "";
        try {
            result = HttpRequest.post(url).send(jsonData.getBytes("utf-8")).body();
        } catch (HttpRequestException | UnsupportedEncodingException e) {
            result = e.getMessage();
        }
        return result;
    }

    public static String sendCustomerImageMsg(String mediaId, String openid, String appid, String secret) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("touser", openid);
        data.put("msgtype", "image");
        Map<String, String> content = new HashMap<String, String>();
        content.put("media_id", mediaId);
        data.put("image", content);
        String accessToken = getAccessToken(appid, secret);
        String url = SEND_CUSTOM_MESSAGE_URL + "=" + accessToken;
        String jsonData = JSON.toJSONString(data);
        String result = "";
        try {
            result = HttpRequest.post(url).send(jsonData.getBytes("utf-8")).body();
        } catch (HttpRequestException | UnsupportedEncodingException e) {
        }
        return result;
    }

    public static String getApiSetIndustry(String appid, String secret) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("industry_id1", 1);
        data.put("industry_id2", 10);
        String accessToken = getAccessToken(appid, secret);
        String url = GET_API_SET_INDUSTRY + accessToken;
        String jsonData = JSON.toJSONString(data);
        String result = "";
        try {
            result = HttpRequest.post(url).send(jsonData.getBytes("utf-8")).body();
        } catch (HttpRequestException | UnsupportedEncodingException e) {
        }
        return result;
    }

    public static String getTemplate(String templateIdShort, String appid, String secret){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("template_id_short", templateIdShort);
        String accessToken = getAccessToken(appid, secret);
        String url = GET_TEMPLATE + accessToken;
        String jsonData = JSON.toJSONString(data);
        String result = "";
        try {
            result = HttpRequest.post(url).send(jsonData.getBytes("utf-8")).body();
        } catch (HttpRequestException | UnsupportedEncodingException e) {
        }
        return result;
    }

    public static String sendTemplate(String opendId, String templateId, String jumpUrl, Map<String, Map<String, Object>> content, String appid, String secret){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("touser", opendId);
        param.put("template_id", templateId);
        param.put("url", jumpUrl);
        param.put("data", content);
        String accessToken = getAccessToken(appid, secret);
        String url = SEND_TEMPLATE + accessToken;
        String jsonData = JSON.toJSONString(param);
        String result = "";
        try {
            result = HttpRequest.post(url).send(jsonData.getBytes("utf-8")).body();
        } catch (HttpRequestException | UnsupportedEncodingException e) {
        }
        return result;
    }

    public static String delTemplate(String templateId, String appid, String secret){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("template_id", templateId);
        String accessToken = getAccessToken(appid, secret);
        String url = DEL_TEMPLATE + accessToken;
        String jsonData = JSON.toJSONString(param);
        String result = "";
        try {
            result = HttpRequest.post(url).send(jsonData.getBytes("utf-8")).body();
        } catch (HttpRequestException | UnsupportedEncodingException e) {
        }
        return result;
    }

    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce) {
        if (signature == null || timestamp == null || nonce == null) {
            return false;
        }
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        // 将 token, timestamp, nonce 三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行 shal 加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest) {
        String strDigest = "";
        for (int i = 0; i < digest.length; i++) {
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexStr(byte b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];

        String s = new String(tempArr);
        return s;
    }


    /**
     * 将文本消息封装成微信规定的xml格式
     *
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String getXmlText(String toUserName, String fromUserName,
                                    String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(toUserName);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(fromUserName);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content></xml>");
        return sb.toString();
    }

    /**
     * 生成单图文消息
     *
     * @param toUsername   收件人
     * @param fromUsername 发件人
     * @param title        标题
     * @param description  描述
     * @param picUrl       图片链接
     * @param url          消息链接（消息点开后转跳到对应链接）
     * @return
     */
    public static String getXmlImageUrl(String toUsername, String fromUsername,
                                        String title, String description, String picUrl, String url) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(toUsername);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(fromUsername);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType>");
        sb.append("<ArticleCount>1</ArticleCount>");
        sb.append("<Articles><item><Title><![CDATA[");
        sb.append(title);
        sb.append("]]></Title><Description><![CDATA[");
        sb.append(description);
        sb.append("]]></Description><PicUrl><![CDATA[");
        sb.append(picUrl);
        sb.append("]]></PicUrl><Url><![CDATA[");
        sb.append(url);
        sb.append("]]></Url></item></Articles></xml>");
        return sb.toString();
    }

    public static String getXmlImg(String toUsername, String fromUsername, String picUrl) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(toUsername);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(fromUsername);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[image]]></MsgType>");
        sb.append("<PicUrl><![CDATA[");
        sb.append(picUrl);
        sb.append("]]></PicUrl><MsgId>5836982871638042400</MsgId></xml>");
        return sb.toString();
    }

    public static JSONObject xml2Json(String xmlData) throws DocumentException {
        Document document = DocumentHelper.parseText(xmlData);
        Element root = document.getRootElement();
        Iterator<?> iter = root.elementIterator();
        JSONObject obj = new JSONObject();
        while (iter.hasNext()) {
            Element ele = (Element) iter.next();
            String key = ele.getName();
            String value = ele.getText();
            obj.put(key, value);
        }
        return obj;
    }

    public static void sendCustomerMsgASync(final String message, final String wechatId, final String appid, final String appsecret) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sendCustomerMsg(message, wechatId, appid, appsecret);
            }
        });
        t.start();
    }

    public static void sendDayCustomerMsgASync(final String message, final String wechatId, final String appid, final String appsecret, final String telephone, final String brandName,final String shopName) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = sendCustomerMsg(message, wechatId, appid, appsecret);
            }
        });
        t.start();
    }

    /**
     * 获取带参数的二维码
     * 参考：https://mp.weixin.qq.com/wiki/18/167e7d94df85d8389df6c94a7a8f78ba.html
     * @param token
     * @param qrParam（二维码的附带参数字符串类型，长度不能超过64）
     * @author lmx
     * @return
     */
    public static String getParamQrCode(String token,String qrParam) {
        String url = GET_PARAM_QRCODE + token;
        JSONObject param2 = new JSONObject();
        param2.put("scene_str", qrParam);
        JSONObject param1 = new JSONObject();
        param1.put("scene", param2);
        JSONObject param = new JSONObject();
        param.put("action_name", "QR_LIMIT_STR_SCENE");
        param.put("action_info", param1);
        String result = HttpClient.doPostJson(url,param.toString());
        return result;
    }

    /**
     * 生成带参数的二维码
     */
    public static String showQrcode(String ticket) {
        String url = SHOW_QRCODE + ticket;
        return url;
    }

    public static String downLoadWXFile(String appid, String secret, String mediaId, String savePath, HttpServletRequest request) {
        String filePath = null;
        String endSavePath = null;
        String endPath = null;
        String accessToken = getAccessToken(appid, secret);
        String requestUrl = GET_FILE + accessToken + "&media_id=" + mediaId;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            String endwith = conn.getContentType();
            String[] end = endwith.split("/");

            String systemPath = request.getServletContext().getRealPath("");
            systemPath = systemPath.replaceAll("\\\\", "/");
            int lastR = systemPath.lastIndexOf("/");
            systemPath = systemPath.substring(0,lastR)+"/";
            systemPath = systemPath.replace("wechat","shop");

            filePath = systemPath + savePath ;
            File file = new File(filePath);
            file.mkdirs();
            endSavePath = savePath + mediaId + "." + end[1];
            endPath = systemPath + endSavePath;

            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(endPath));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PictureResult picResult = UploadFilesUtil.uploadPic(new File(endPath));
        if(picResult.getError() == 0){
            return picResult.getUrl();
        }else{
            return null;
        }
    }
}
