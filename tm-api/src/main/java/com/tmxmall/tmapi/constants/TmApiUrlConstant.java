package com.tmxmall.tmapi.constants;

public class TmApiUrlConstant {

    private static String[] hosts = new String[]{"http://api.tmxmall.com"};  // 配置请求的host

    private static String serverName = hosts[0];
    
    public static final String CLIENTID_VERIFY = serverName + "/v1/http/clientIdVerify";

    public static final String TRANSLATE_TM = serverName + "/v1/http/translate";

    public static final String SET_TM = serverName + "/v1/http/set";
    
}
