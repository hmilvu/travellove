package com.travel.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.AuthorityAction;
import com.travel.service.MemberService;
import com.travel.utils.JsonUtils;

public class FileUploadAction extends AuthorityAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemberService memberService;

	private File upload;   
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	private String importBizType;
	
	public String getImportBizType() {
		return importBizType;
	}

	public void setImportBizType(String importBizType) {
		this.importBizType = importBizType;
	}

	@Override
	public String execute(){
		int result = 0;
		List <Integer>errorList = new ArrayList<Integer>();
		if(StringUtils.equals(importBizType, "IMPORT_TEAM_MEMBER")){
			String teamId = request.getParameter("teamId");
			try {
				errorList = memberService.importMembers(teamId, upload, getCurrentUser());
			} catch (Throwable e) {
				log.error("导入会员错误 teamId = " + teamId, e);
				result = 1;
			}
		}
		if(result == 0){
			if(errorList.size() > 0){
				JsonUtils.write(response, "{\"code\":\"-1\",\"msg\":\""+errorList.toString()+"\"}");
			} else {
				JsonUtils.write(response, "{\"code\":\"0\",\"msg\":\"success\"}");
			}
		} else if(result == 1){
			JsonUtils.write(response, "{\"code\":\"-1\",\"msg\":\""+errorList.toString()+"\"}");
		}
		return null;
	}  
}
