import com.fasterxml.jackson.core.type.TypeReference;
import com.tmxmall.docTrans.DocTransUrlConstants;
import com.tmxmall.docTrans.FileUtil;
import com.tmxmall.docTrans.HttpClientUtil;
import com.tmxmall.docTrans.JacksonUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class DocTransDemo {

    private static Logger logger = Logger.getLogger(DocTransDemo.class);
    public static void main(String[] args) throws IOException, URISyntaxException{

        //文件上传
        String uploadUrl = DocTransUrlConstants.upload;
        uploadFile(uploadUrl);
        //文件下载
        String downloadUrl = DocTransUrlConstants.download;
        downloadFile(downloadUrl);
        //获取文件进度
        String getProgressUrl = DocTransUrlConstants.getProgress;
        getFileProgress(getProgressUrl);
    }

    public static void uploadFile(String url) throws IOException{
        // params用于存储要请求的参数
        Map<String, String> params = new HashMap<String, String>();
        // 按接口要求传递参数
        File file = new File("");
        params.put("user_name", "");
        params.put("client_id", "");
        params.put("from", "");
        params.put("to", "");
        String response = HttpClientUtil.uploadFileByPost(url, file.getAbsolutePath(),
                "file_api_trans", params, 5 * 10000);
        Map<String, Object> parseResult = JacksonUtil.readValue(response, new TypeReference<Map<String, Object>>(){});
        logger.info(parseResult.toString());
    }


    public static void downloadFile(String url) throws IOException, URISyntaxException{
        // params用于存储要请求的参数
        Map<String, String> params = new HashMap<String, String>();
        // 接口要求传递参数
        params.put("user_name", "");
        params.put("client_id", "");
        params.put("doc_id", "");
        params.put("is_ie", "");
        InputStream inputStream = HttpClientUtil.doGetInputStream(url, params);
        if(inputStream == null){
            throw new RuntimeException("文件下载失败！");
        }
        String downloadFilePath = "";          // 下载文件的路径
        FileUtil.inputStreamToFile(inputStream, downloadFilePath);
        logger.info("success");
    }


    public static void getFileProgress(String url) throws IOException, URISyntaxException{
        // params用于存储要请求的参数
        Map<String, String> params = new HashMap<String, String>();
        // 接口要求传递参数：用户Tmxmall邮箱账号，用户clientId，调用方，选择的引擎，领域（非必填）
        params.put("user_name", "");
        params.put("client_id", "");
        params.put("doc_id", "");
        String result = HttpClientUtil.doGetStr(url, params);
        logger.info(result);
    }

}
