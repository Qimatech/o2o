package com.qima.o2o.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShortNetAddressUtil {

	private static Logger log = LoggerFactory.getLogger(ShortNetAddressUtil.class);
	
	public static int TIMEOUT = 30*1000;
	public static String ENCODING = "UTF-8";
	
	public static String getShortURL(String originURL) {
		String tinyUrl = null;
		try {
			URL url = new URL("http://dwz.cn/create.php");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(TIMEOUT);
			connection.setRequestMethod("POST");
			String postData = URLEncoder.encode(originURL.toString(),"utf-8");
			connection.getOutputStream().write(("url="+postData).getBytes());
			connection.connect();
			String responseStr = getResponseStr(connection);
			log.info("response string:" + responseStr);
			
			tinyUrl = getValueByKey(responseStr,"tinyurl");
			log.info("tinyurl:" + tinyUrl);
			connection.disconnect();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tinyUrl;
	}

	private static String getValueByKey(String replyText, String key) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		String targetValue = null;
		try {
			node = mapper.readTree(replyText);
			targetValue = node.get(key).asText();
		} catch (JsonProcessingException e) {
			log.error("getValueByKey error:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("getValueByKey error:" + e.toString());
		}
		return targetValue;
	}

	private static String getResponseStr(HttpURLConnection connection) throws IOException {
		StringBuffer result = new StringBuffer();
		int responseCode = connection.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,ENCODING));
			String inputLine = "";
			while((inputLine = reader.readLine()) != null) {
				result.append(inputLine);
			}
		}
		
		return String.valueOf(result);
	}
	
	
	public static void main(String[] args) {
		getShortURL("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
	}
}
