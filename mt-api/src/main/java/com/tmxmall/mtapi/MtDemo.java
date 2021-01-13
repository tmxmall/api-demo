package com.tmxmall.mtapi;

import com.tmxmall.mtapi.constants.MtApiUrlConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * 机器翻译API接入的demo
 *
 */
public class MtDemo {
	private static Logger logger = Logger.getLogger(MtDemo.class);
	public static void main(String[] args) {
		
		//传参
        String from = MtApiUrlConstant.FROM;
        String to = MtApiUrlConstant.TO;
        String text = MtApiUrlConstant.TEXT;
        String clientId = MtApiUrlConstant.CLIENT_ID;
        String provider = MtApiUrlConstant.PROVIDER;
        String username = MtApiUrlConstant.USERNAME;

        //翻译接口地址
		String mtTransUrl = MtApiUrlConstant.MT_TRANSLATE;
		mtTranslate(mtTransUrl,from,to,text,username,provider,clientId);
		
	}
	
	/**
	 * 调用用户ClientId验证接口
	 * @param url
	 */
	public static void testClientId(String url) {
		// params用于存储要请求的参数
		Map<String, String> params = new HashMap<String, String>();
		//按接口要求传递参数
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		String result = urlConnection(url, params);
		logger.info("---调用用户ClientId验证接口---"+result);
	}

	/**
	 *调用翻译接口
	 * @param url
	 */
	public static void mtTranslate(String url,String from,String to, String text,String username,String provider,String clientId) {
		//params用于存储要请求的参数
		Map<String, String> params = new HashMap<String, String>();
		//封装签名
        String sign = DigestUtils.md5Hex(username + from + text + to + clientId);
		//按接口要求传递参数
		params.put("user_name", username);
		params.put("provider", provider);
		params.put("text", text);
		params.put("from", from);
		params.put("to", to);
		params.put("sign",sign);
		String result = urlConnection(url, params);
		logger.info("---调用翻译接口---"+result);
		
	}

	/**
	 * 调用机器引擎接口
	 * @param url
	 */
	public static void setMtProvider(String url) {
		//params用于存储要请求的参数
		Map<String, String> params = new HashMap<String, String>();
		// 按接口要求传递参数：用户Tmxmall邮箱账号，用户clientId，调用方，选择的引擎，领域（非必填）
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		params.put("mt_provider", "");
		params.put("mt_filed", "");
		String result = urlConnection(url, params);
		logger.info("---调用机器引擎接口---"+result);
	}
	
	/**
	 *主要用于请求地址，并加上请求参数
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String urlConnection(String requestUrl, Map<String,String> params) {
		// buffer用于接受返回的字符
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl + "?" + paramsFilter(params));
			//打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            //传递参数需要开启输入
			httpUrlConn.setDoInput(true);
			//提交方式
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			//构造一个字符流缓存
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			//将bufferReader的值给放到buffer里
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            //关流
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null; 
            //断开连接
            httpUrlConn.disconnect();
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 将map里的参数变成像 user_name=###&client_id=###的样子
	 * 
	 * @param params
	 * @return
	 */
	public static String paramsFilter(Map<String,String> params) {

		StringBuffer buffer = new StringBuffer();
		params.forEach((k, v) -> {
			try {
				//对参数中的中文进行处理
				buffer.append(k).append("=").append(URLEncoder.encode(v+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		return buffer.toString();
	}

}
