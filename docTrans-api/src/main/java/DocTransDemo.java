import com.fasterxml.jackson.core.type.TypeReference;
import com.tmxmall.docTrans.DocTransUrlConstants;
import com.tmxmall.docTrans.FileUtil;
import com.tmxmall.docTrans.HttpClientUtil;
import com.tmxmall.docTrans.JacksonUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DocTransDemo {

    private static Logger logger = Logger.getLogger(DocTransDemo.class);
    //待翻译的源文件
    public static String DOWNLOAD_FILE_PATH_NAME = "C:\\Users\\user\\Desktop\\tmp\\dockTrans\\result.docx";
    //翻译后的文件保存目录
    public static String UPLOAD_FILE_PATH_NAME = "C:\\Users\\user\\Desktop\\tmp\\dockTrans\\上海市房屋租赁合同(中文).docx";
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        //文件上传
        String uploadUrl = DocTransUrlConstants.upload;
        String docId = uploadFile(uploadUrl);
        //获取文件进度
        String getProgressUrl = DocTransUrlConstants.getProgress;
        String percent;
        while(!(percent = getFileProgress(getProgressUrl, docId)).endsWith("100.0%")){
            System.out.println("[tranlation progress]" + percent);
            Thread.sleep(1*1000);
        }
        //文件下载
        String downloadUrl = DocTransUrlConstants.download;
        downloadFile(downloadUrl, docId);
    }

    public static String uploadFile(String url) throws IOException{
        // params用于存储要请求的参数
        Map<String, String> params = new HashMap<String, String>();
        // 按接口要求传递参数
        File file = new File(UPLOAD_FILE_PATH_NAME);
        params.put("from", "zh-CN");
        params.put("to", "ja-JP");
        String response = HttpClientUtil.uploadFileByPost(url, file.getAbsolutePath(),
                "file_api_trans", params, 5 * 10000);
        Map<String, Object> parseResult = JacksonUtil.readValue(response, new TypeReference<Map<String, Object>>(){});
        logger.info(parseResult.toString());
        return (String)((LinkedHashMap)parseResult.get("data")).get("docId");
    }


    public static void downloadFile(String url, String docId) throws IOException, URISyntaxException{
        // params用于存储要请求的参数
        Map<String, String> params = new HashMap<String, String>();
        // 接口要求传递参数
        params.put("doc_id", docId);
        params.put("is_ie", "false");
        InputStream inputStream = HttpClientUtil.doGetInputStream(url, params);
        if(inputStream == null){
            throw new RuntimeException("文件下载失败！");
        }
        FileUtil.inputStreamToFile(inputStream, DOWNLOAD_FILE_PATH_NAME);
        logger.info("success");
    }


    public static String getFileProgress(String url, String docId) throws IOException, URISyntaxException{
        // params用于存储要请求的参数
        Map<String, String> params = new HashMap<String, String>();
        // 接口要求传递参数：用户Tmxmall邮箱账号，用户clientId，调用方，选择的引擎，领域（非必填）
        params.put("doc_id", docId);
        String result = HttpClientUtil.doGetStr(url, params);
        Map<String, Object> parseResult = JacksonUtil.readValue(result, new TypeReference<Map<String, Object>>(){});
        return (String)((LinkedHashMap)parseResult.get("data")).get("percent");
    }

}
