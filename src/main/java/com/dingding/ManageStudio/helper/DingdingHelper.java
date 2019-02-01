package com.dingding.ManageStudio.helper;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@EnableAutoConfiguration
@Component
public class DingdingHelper {
	@Autowired
	private Environment environment;
	private static final Logger log=LoggerFactory.getLogger(DingdingHelper.class);
	/**
	 * 获取access_token
	 * @return
	 */
	public String GetAccessToken() {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String, String>();
		map.put("appkey", environment.getProperty("dingding_app_key"));
		map.put("appsecret", environment.getProperty("dingding_app_secret"));
		try {
			String result=HttpClientHelper.doGet(environment.getProperty("access_token_url"), map);
			log.info("access token info:"+result);
			JSONObject object=JSONObject.fromObject(result);
			if((int)object.get("errcode")==0) {
				return object.getString("access_token").toString();
			}else {
				log.error("access_api_error:"+object.getString("errmsg").toString());
				return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return null;
		}
	}
	public void CreaterUser() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", "方芳");
		map.put("department",new int[] {1});
		map.put("mobile", "");
	}
	/**
	 * 获取部门列表
	 * @return
	 */
	public String GetDepartmentList() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("access_token",GetAccessToken());
		try {
			String result=HttpClientHelper.doGet(environment.getProperty("dep_list_url"),map);
			log.info("dep list info:"+result);
			JSONObject object=JSONObject.fromObject(result);
			if((int)object.get("errcode")==0) {
				return object.getString("department").toString();
			}else {
				log.error("dep_list_api_error:"+object.getString("errmsg").toString());
				return null;
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return null;
		}
	}
	
}
