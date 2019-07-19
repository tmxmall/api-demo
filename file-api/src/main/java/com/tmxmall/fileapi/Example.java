package com.tmxmall.fileapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tmxmall.fileapi.constants.FileApiUrlConstant;
import com.tmxmall.fileapi.model.SimpleSegment;
import com.tmxmall.fileapi.trans.SegmentTranslator;
import com.tmxmall.fileapi.utils.FileUtil;
import com.tmxmall.fileapi.utils.HttpClientUtil;
import com.tmxmall.fileapi.utils.JacksonUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Example {

    public static void main(String[] args) throws IOException, URISyntaxException {
        File file = new File("");          // 上传的文件,自行设置
        String userName = "";                   // 用户名
        String clientId = "";                   // 用户中心获取api key
        String from = "";                  // 详见语言表
        String to = "";                    // 详见语言表
        String de = "";

        Map<String, Object> parseResult = parseFile(file.getAbsolutePath(), userName, clientId, from, to, de);
        if(!"0".equals(String.valueOf(parseResult.get("errCode")))){
            throw new RuntimeException((String) parseResult.get("errMsg"));
        }
        String docId = (String) parseResult.get("docId");
        String jsonSegments = JacksonUtil.toJSon(parseResult.get("segments"));
        List<SimpleSegment> segments = JacksonUtil.readValue(jsonSegments, new TypeReference<List<SimpleSegment>>(){});

        // TODO 可以调用机器翻译，或者调用记忆库进行翻译
        segments = new SegmentTranslator(segments, (sentence) -> "测试文本").trans();

        InputStream inputStream = exportFile(userName, clientId, docId, segments);
        if(inputStream == null){
            throw new RuntimeException("文件下载失败！");
        }
        String downloadFilePath = "";          // 下载文件的路径

        FileUtil.inputStreamToFile(inputStream, downloadFilePath);
    }

    public static Map<String, Object> parseFile(String filePath, String userName, String clientId, String from,
                                                String to, String de) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("user_name", userName);
        params.put("client_id", clientId);
        params.put("from", from);
        params.put("to", to);
        params.put("de", de);

        String response = HttpClientUtil.uploadFileByPost(FileApiUrlConstant.PARSE_FILE, filePath,
                                    "file", params, 5 * 10000);
        return JacksonUtil.readValue(response, new TypeReference<Map<String, Object>>(){});
    }

    public static InputStream exportFile(String userName, String clientId, String docId,
                                                 List<SimpleSegment> segments) throws IOException, URISyntaxException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user_name", userName);
        parameters.put("client_id", clientId);
        parameters.put("doc_id", docId);
        parameters.put("segments", JacksonUtil.toJSon(segments));

        return HttpClientUtil.doPostInputStream(FileApiUrlConstant.EXPORT_FILE, parameters);
    }
}
