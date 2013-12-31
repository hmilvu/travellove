package com.travel.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
	public static String post(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:" + url);
		System.out.println("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}
	
//	public static String postServer(String url, Map<String, String> params){
//		StringBuffer response = new StringBuffer();
//        HttpClient client = new HttpClient();
//        PostMethod postMethod = new PostMethod(url);
//        //表单域的值
//        NameValuePair[] data = new NameValuePair[params.size()];
//        Iterator<String> it = params.keySet().iterator();
//        int i = 0;
//        while(it.hasNext()){
//        	String key = it.next();
//        	String value = params.get(key);
//        	data[i] = new NameValuePair(key, value);
//        	i++;
//        }
//        postMethod.setRequestBody(data);
//        //解决中文乱码问题
//        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//        try {
//            int statusCode = client.executeMethod(postMethod);
//            if (statusCode == HttpStatus.SC_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    postMethod.getResponseBodyAsStream(), "UTF-8"));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }finally {
//            postMethod.releaseConnection();
//        }
//        return response.toString();
//    }
}
