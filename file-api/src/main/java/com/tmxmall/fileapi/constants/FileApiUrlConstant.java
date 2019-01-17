package com.tmxmall.fileapi.constants;

public class FileApiUrlConstant {

    private static String[] hosts = new String[]{"http://api.tmxmall.com"};  // 配置请求的host

    private static String serverName = hosts[0];

    public static final String PARSE_FILE = serverName + "/v1/http/parseFile";

    public static final String EXPORT_FILE = serverName + "/v1/http/exportFile";
}
