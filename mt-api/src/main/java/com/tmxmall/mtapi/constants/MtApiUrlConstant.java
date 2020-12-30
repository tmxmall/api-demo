package com.tmxmall.mtapi.constants;

public class MtApiUrlConstant {

    private static String[] hosts = new String[]{"http://api.tmxmall.com"};  // 配置请求的host

    private static String serverName = hosts[0];

    public static final String SET_MTPROVIDER = serverName + "/v1/http/setmtprovider";

    public static final String MT_TRANSLATE = serverName + "/v1/http/mttranslate";
    
    public static final String CLIENTID_VERIFY = serverName + "/v1/http/clientIdVerify";
}
