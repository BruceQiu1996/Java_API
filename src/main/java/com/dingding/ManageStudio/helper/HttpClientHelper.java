package com.dingding.ManageStudio.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientHelper{
	private static final Logger log=LoggerFactory.getLogger(HttpClientHelper.class);
	/**
	 * get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url,Map<?, ?> params) {
		StringBuffer sb=new StringBuffer();
		for(Iterator<?> iterator=params.keySet().iterator();iterator.hasNext();) {
			String name=(String)iterator.next();
			sb.append(name+"="+String.valueOf(params.get(name))+"&");
		}
		String reqUrlString=url+"?"+sb.toString().substring(0, sb.toString().length()-1);
		log.info("Get requestUrl is :"+reqUrlString);
		try {
			HttpClient client=new DefaultHttpClient();
			HttpGet requestGet=new HttpGet(reqUrlString);
			HttpResponse response=client.execute(requestGet);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String strResultString=EntityUtils.toString(response.getEntity());
				return strResultString;
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public static String doPost(String url,Map<?, ?> params) {
		BufferedReader reader=null;
		try {
			HttpClient client=new DefaultHttpClient();
			HttpPost request=new HttpPost();
			request.setURI(new URI(url));
			List<NameValuePair> nvpsList=new ArrayList<NameValuePair>();
			for(Iterator<?> iterator=params.keySet().iterator();iterator.hasNext();) {
				String name=(String)iterator.next();
				String value=String.valueOf(params.get(name));
				nvpsList.add(new BasicNameValuePair(name, value));
			}
			request.setEntity(new UrlEncodedFormEntity(nvpsList,HTTP.UTF_8));
			HttpResponse response=client.execute(request);
			int code=response.getStatusLine().getStatusCode();
			if(code==200) {
				reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));
				StringBuffer sBuffer=new StringBuffer();
				String line="";
				String NL=System.getProperty("line.separator");
				while((line=reader.readLine())!=null) {
					sBuffer.append(line+NL);
				}
				reader.close();
				return sBuffer.toString();
			}else {
				log.error("POST request error code is："+code);
				return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return null;
		}
	}
}
