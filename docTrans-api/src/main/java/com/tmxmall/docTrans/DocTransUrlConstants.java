package com.tmxmall.docTrans;

public class DocTransUrlConstants {
    private static String[] hosts = new String[]{"https://www.tmxmall.com"};  // 配置请求的host

    private static String serverName = hosts[0];

    public static final String upload = serverName + "/qt/v1/http/qtFile/_upload";

    public static final String download = serverName + "/qt/v1/http/qtFile/_download";

    public static final String getProgress = serverName + "/qt/v1/http/transProgress";
}
