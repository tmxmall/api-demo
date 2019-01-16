package com.tmxmall.tmapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 翻译记忆API接入的demo
 *
 */
public class TmDemo {
	
	private static Logger logger = Logger.getLogger(TmDemo.class);
	
	public static void main(String[] args) {
		
		//用户ClientId验证接口
		String testCidUrl = "";
		testClientId(testCidUrl);
		//查询翻译记忆接口地址
		String setmturl = "";
		searchTm(setmturl);
		// 写入接口地址
		String mtTransUrl = "";
		writeTm(mtTransUrl);
	}
	
	/**
	 * 调用用户ClientId验证接口
	 * @param url
	 */
	public static void testClientId(String url) {
		// params用于存储要请求的参数
		Map<String, String> params = new HashMap<String, String>();
		// 按接口要求传递参数
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		String result = urlConnection(url, params);
		logger.info("---调用用户ClientId验证接口---"+result);
	}

	/**
	 * 调用查询翻译记忆接口
	 * 
	 * @param url
	 */
	public static void searchTm(String url) {
		// params用于存储要请求的参数
		Map<String, String> params = new HashMap<String, String>();
		// 接口要求传递参数
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		params.put("text", "");
		params.put("fuzzy_threshold", "");
		params.put("tu_num", "");
		params.put("gls_num", "");
		String result = urlConnection(url, params);
		logger.info("---调用查询翻译记忆接口---"+result);
	}

	/**
	 * 调用写入翻译记忆接口
	 * 
	 * @param url
	 */
	public static void writeTm(String url) {
		// params用于存储要请求的参数
		Map<String, String> params = new HashMap<String, String>();
		// 接口要求传递参数：用户Tmxmall邮箱账号，用户clientId，调用方，选择的引擎，领域（非必填）
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("seg", "");
		params.put("tra", "");
		params.put("de", "");
		params.put("version", "");
		params.put("from", "");
		params.put("to", "");
		String result = urlConnection(url, params);
		logger.info("---调用写入翻译记忆接口---"+result);
	}

	/**
	 * 主要用于请求地址，并加上请求参数
	 * 
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String urlConnection(String requestUrl, Map<String,String> params) {
		// buffer用于接受返回的字符
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl + "?" + paramsFilter(params));
			// 打开http连接
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			// 传递参数需要开启输入
			httpUrlConn.setDoInput(true);
			// 提交方式
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			// 构造一个字符流缓存
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			// 将bufferReader的值给放到buffer里
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 关流
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			// 断开连接
			httpUrlConn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
				// 对参数中的中文进行处理
				buffer.append(k).append("=").append(URLEncoder.encode(v + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		return buffer.toString();
	}
}
