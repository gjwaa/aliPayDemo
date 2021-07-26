package com.gjw.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.gjw.config.AlipayConfig;
import org.bouncycastle.asn1.ocsp.ResponseData;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author gjw
 * @version 1.0
 * @date 2021/7/26 21:27
 */
@Controller
public class TestController {

    @RequestMapping
    @ResponseBody
    public String testAli() {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.APPID,AlipayConfig.RSA_PRIVATE_KEY,
                AlipayConfig.format,AlipayConfig.charset,AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.sign_type);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setReturnUrl(AlipayConfig.return_url);
        request.setNotifyUrl(AlipayConfig.notify_url);//在公共参数中设置回跳和通知地址
        // 支付请求
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + UUID.randomUUID() + "\"," +//商户订单号
                "    \"total_amount\":\"" + "100" + "\"," +
                "    \"subject\":\"" + "外卖" + "当面付" + "\"," +
                "    \"store_id\":\"00100\"," + // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
                "    \"timeout_express\":\"5m\"}");//订单允许的最晚付款时间
        AlipayTradePrecreateResponse response = null;
        //支付宝二维码URL
        String qrCode = "";
        try {
            response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                System.out.println(response.getMsg());
            }
            qrCode = response.getQrCode();
        } catch (AlipayApiException e) {
            System.out.println("!!!!");
        }

        // 封装支付信息 返回
        System.err.println(response.getBody());
        return qrCode;
//        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest ();//创建API对应的request
//
//        //设置同步返回地址，HTTP/HTTPS开头字符串
//        alipayRequest.setReturnUrl(AlipayConfig.return_url);
//
//        //支付宝服务器主动通知商户服务器里指定的页面http/https路径。
//        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);//在公共参数中设置回跳和通知地址
//
//        //商户订单号，不能为空
//        String out_trade_no = "12"+ UUID.randomUUID();
//
//        //付款金额，不能为空，单位:元
//        String total_amount = "10";
//
//        //订单名称，不能为空
//        String subject = "1";
//
//        //商品描述，可空
//        String body = "202111111111";
//
//        //该笔订单最晚付款时间，逾期关闭交易。取值范围:1m~15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）参数数值不接受小数点， 如 1.5h，可转换为 90m。
//        String timeout_express = "1c";
//
//        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
//                + "\"total_amount\":\""+ total_amount +"\","
//                + "\"subject\":\""+ subject +"\","
//                + "\"body\":\""+ body +"\","
//                + "\"timeout_express\":\""+ timeout_express +"\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//        //填充业务参数
//        String form="";
//        try {
//            //调用SDK生成表单
//            //form = alipayClient.pageExecute(alipayRequest).getBody();
//            AlipayTradePrecreateResponse responseData = alipayClient.execute(alipayRequest);
//            form = responseData.getBody();
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        System.err.println(form);
//        System.err.println(ResponseData.getInstance(true));
//        return form;
    }
    
    @RequestMapping("/notifyUrl")
    public String notifyUrl(){
        System.err.println("notifyUrl");
        return "index";
    }
    @RequestMapping("/returnUrl")
    public String returnUrl(){
        System.err.println("returnUrl");
        return "index";
    }

}
