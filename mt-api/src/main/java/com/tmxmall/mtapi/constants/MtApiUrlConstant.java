package com.tmxmall.mtapi.constants;

public class MtApiUrlConstant {

    private static String[] hosts = new String[]{"http://api.tmxmall.com"};  // 配置请求的host

    private static String serverName = hosts[0];


    public static final String MT_TRANSLATE = serverName + "/v3/http/mttranslate";

    public static final String USER_NAME = "xxx";
    public static final String FROM = "en-US";
    public static final String TO = "zh-CN";
    public static final String TEXT = "Compilers and processors must both obey reordering rules. No particular effort is required to ensure that uniprocessors maintain proper ordering, since they all guarantee as-if-sequential consistency. But on multiprocessors, guaranteeing conformance often requires emitting barrier instructions. Even if a compiler optimizes away a field access (for example because a loaded value is not used), barriers must still be generated as if the access were still present. (Although see below about independently optimizing away barriers.";
    public static final String MT_PROVIDER = "Youdao";
    public static final String CLIENT_ID = "xxx";

}
