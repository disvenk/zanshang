package com.resto.brand.core.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KONATA on 2016/11/20.
 * 支付宝工具类
 */
public class AliPayUtils {


//    private static final String baseUrl = "https://openapi.alipaydev.com/gateway.do"; //沙箱环境
    private static final String baseUrl = "https://openapi.alipay.com/gateway.do"; //正式环境

    private static AlipayClient alipayClient;


    public static void connection(String appId, String privateKey, String publicKey) {
        alipayClient = new DefaultAlipayClient(baseUrl, appId, privateKey, "json", "GBK", publicKey);
    }

    public static void phonePay(HttpServletResponse httpResponse, Map map, String returnUrl, String nofityUrl) throws Exception {
        JSONObject jsonObject = new JSONObject(map);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        if(!StringUtils.isEmpty(returnUrl)){
            alipayRequest.setReturnUrl(returnUrl); //支付完成后回调地址
        }

        alipayRequest.setNotifyUrl(nofityUrl);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(jsonObject.toString());
        String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        httpResponse.setContentType("text/html;");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
    }


    public static String refundPay(Map map){
        JSONObject jsonObject = new JSONObject(map);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(jsonObject.toString());
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return e.getMessage();

        }
        return response.getBody();
    }

    public static void main(String[] args) throws AlipayApiException {
        connection("2016102800773386",
                        "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANP6llbFMPEF+Kzn" +
                        "ZbYUF4Tw30zVIR15geFQWCCYMveflqCyCIYzDctEBhoeXj674Nofv3xyIxVZSTPe" +
                        "SpGOhjjZRDke1+M++8AUZlIIdDZfkOuoFSxeGYja5m2hWCvUvVg8YbnspRuwjda+" +
                        "fYeWsk6/aDukpULZqTgkii3f2reXAgMBAAECgYBUXlQfzPQhueKzzpVo1q5Vtxjp" +
                        "F5rKhGXxK20n6+u9KsNkyfcikodW84gKNTQFe/mOVzx7Z2IXSSYdgsfjDvrUQ72G" +
                        "UWBESSd+g91SmP0sLwTBk7kQDTzPyNXTKDWdK9ouC3Oho5i+2FLGrdBLizozQpEC" +
                        "ApNbPeSmiy7dGil+gQJBAPeb3dIH4kSWZWkjdBM5RPBARpZAx1PSyJtHlVR+z2TW" +
                        "1mPmT9lbecZlPt2YHTvxGClWZ9nwpxcYi3QasCVf93ECQQDbKZzA6WMl2z+gbXoQ" +
                        "PepJvHDaIt+KhegeRlujAyg1ugx89KvC3UHNF4ajd8FQsAh9c88fzY79LZVpNUTf" +
                        "g2uHAkEA7Pc+UsM4yGsmonhLnhow37yj0Sgtmwse8XyQbUzvLpJsmy7PPDVPVY+P" +
                        "moL5d2REu0r2GJ03S+Mxkuv3p80wAQJBAI8G/yfeqDgCd+moyKpk3cu1USjq7Vwn" +
                        "u65WWGNwIgO+IXxC6P1JDDJekh2If/66gy/sLlYg/po373QzsXj0+W0CQQCu11Ly" +
                        "56Wz2g/8oHFxHgXy0B1QpUyw8nLq5zoUnPXGx+w1pSA+dtXHo9E+cyFq4l1SWQ6B" +
                        "KsZHaJMNcA9svoJi",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB"
        );
        Map map = new HashMap();
        map.put("out_trade_no","ac10efee57e04a30b3f2cbd0652ee5d3");
        map.put("trade_no","2016112121001004850200065496");
        map.put("refund_amount","0.01");
        refundPay(map);









    }


}
