package com.tmxmall.mtapi;

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
 * ��������API�����demo
 *
 */
public class MtDemo {
	private static Logger logger = Logger.getLogger(MtDemo.class);
	public static void main(String[] args) {
		
		//�û�ClientId��֤�ӿ�
		String testCidUrl = "";
		testClientId(testCidUrl);
		// ���û�������ӿڵ�ַ
		String setmturl = "";
		setMtProvider(setmturl);
		//����ӿڵ�ַ
		String mtTransUrl = "";
		mtTranslate(mtTransUrl);
		
	}
	
	/**
	 * �����û�ClientId��֤�ӿ�
	 * @param url
	 */
	public static void testClientId(String url) {
		// params���ڴ洢Ҫ����Ĳ���
		Map<String, String> params = new HashMap<String, String>();
		// ���ӿ�Ҫ�󴫵ݲ���
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		String result = urlConnection(url, params);
		logger.info("---�����û�ClientId��֤�ӿ�---"+result);
	}
	
	/**
	 * ���÷���ӿ�
	 * @param url
	 */
	public static void mtTranslate(String url) {
		// params���ڴ洢Ҫ����Ĳ���
		Map<String, String> params = new HashMap<String, String>();
		// ���ӿ�Ҫ�󴫵ݲ���
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		params.put("text", "");
		params.put("from", "");
		params.put("to", "");
		String result = urlConnection(url, params);
		logger.info("---���÷���ӿ�---"+result);
		
	}

	/**
	 * ���û�������ӿ�
	 * @param url
	 */
	public static void setMtProvider(String url) {
		// params���ڴ洢Ҫ����Ĳ���
		Map<String, String> params = new HashMap<String, String>();
		// ���ӿ�Ҫ�󴫵ݲ������û�Tmxmall�����˺ţ��û�clientId�����÷���ѡ������棬���򣨷Ǳ��
		params.put("user_name", "");
		params.put("client_id", "");
		params.put("de", "");
		params.put("mt_provider", "");
		params.put("mt_filed", "");
		String result = urlConnection(url, params);
		logger.info("---���û�������ӿ�---"+result);
	}
	
	/**
	 *��Ҫ���������ַ���������������
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String urlConnection(String requestUrl, Map<String,String> params) {
		// buffer���ڽ��ܷ��ص��ַ�
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl + "?" + paramsFilter(params));
			//��http����
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            //���ݲ�����Ҫ��������
			httpUrlConn.setDoInput(true);
			//�ύ��ʽ
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			//����һ���ַ�������
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			//��bufferReader��ֵ���ŵ�buffer��
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            //����
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null; 
            //�Ͽ�����
            httpUrlConn.disconnect();
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * ��map��Ĳ�������� user_name=###&client_id=###������
	 * 
	 * @param params
	 * @return
	 */
	public static String paramsFilter(Map<String,String> params) {

		StringBuffer buffer = new StringBuffer();
		params.forEach((k, v) -> {
			try {
				//�Բ����е����Ľ��д���
				buffer.append(k).append("=").append(URLEncoder.encode(v+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		return buffer.toString();
	}

}
