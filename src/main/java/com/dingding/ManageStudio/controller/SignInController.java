package com.dingding.ManageStudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingding.ManageStudio.helper.DingdingHelper;


@RestController
public class SignInController {
	@Autowired
	private DingdingHelper ddUtil;
	@GetMapping("/signIn")
	public String GetInfo() {
		String str;
		if((str=ddUtil.GetAccessToken())!=null)
			return str;
		else
			return "ERROR";
	}
	@GetMapping("/depList")
	
	
	public String GetDepList() {
		String str;
		if((str=ddUtil.GetDepartmentList())!=null)
			return str;
		else
			return "ERROR";
	}
}
