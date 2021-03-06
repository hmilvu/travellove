package com.travel.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.AuthorityAction;
import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.Constants.ITEM_TYPE;
import com.travel.common.Constants.VIEW_SPOT_TYPE;
import com.travel.entity.ItemInf;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.FileService;
import com.travel.service.ImgService;
import com.travel.service.ItemInfService;
import com.travel.service.ViewSpotService;
import com.travel.utils.Config;
import com.travel.utils.JsonUtils;

public class MultiFileUploadAction extends AuthorityAction {
	/**
	 * 
	 */
	private static final String VIEW_SPOT_IMAGE = "VIEW_SPOT_IMAGE";
	/**
	 * 
	 */
	private static final String ITEM_IMAGE = "ITEM_IMAGE";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ViewSpotService viewSpotService;
	@Autowired
	private ItemInfService itemService;
	@Autowired
	private ImgService imgService;
	@Autowired
	private FileService fileService;
	private File upload1;
	private File upload2;
	private File upload3;
	private String upload1FileName;
	private String upload2FileName;
	private String upload3FileName;
	private String imgId1;
	private String imgId2;
	private String imgId3;
	private String uploadBizType;

	@Override
	public String execute() {
		int result = 0;
		List<Integer> errorList = new ArrayList<Integer>();
		String serverUrl = "http://" + Config.getProperty("ip.address") + ":" + request.getLocalPort() + getRelativePath();
		if (StringUtils.equals(uploadBizType, VIEW_SPOT_IMAGE)) {
			String viewSpotId = request.getParameter("viewSpotId");
			ViewSpotInfo view = viewSpotService.getViewSpotById(Long.valueOf(viewSpotId));
			if(view.getType().intValue() == VIEW_SPOT_TYPE.PUBLIC.getValue() && isTravelUser()){
				JsonUtils.write(response, "{\"code\":\"-2\",\"msg\":\"\"}");
				return null;
			}
			String folderName = "viewspot";
			if(StringUtils.isBlank(imgId1)){//新增或者删除
				if(upload1 != null){
					boolean uploadResult = uploadFile(folderName + "\\" + viewSpotId, "1", upload1, upload1FileName);
					if(uploadResult){
						String extFileName = upload1FileName.substring(upload1FileName.lastIndexOf("."));	
						imgService.saveImageInf(IMAGE_TYPE.VIEWSPOT, viewSpotId, "1", extFileName, serverUrl, getCurrentUser());
					}
				} else {
					imgService.deleteByName(IMAGE_TYPE.VIEWSPOT, viewSpotId, "1");
				}
			}
			if(StringUtils.isBlank(imgId2)){//新增或者删除
				if(upload2 != null){
					boolean uploadResult = uploadFile(folderName + "\\" + viewSpotId, "2", upload2, upload2FileName);
					if(uploadResult){
						String extFileName = upload2FileName.substring(upload2FileName.lastIndexOf("."));	
						imgService.saveImageInf(IMAGE_TYPE.VIEWSPOT, viewSpotId, "2", extFileName, serverUrl, getCurrentUser());
					} 
				} else {
					imgService.deleteByName(IMAGE_TYPE.VIEWSPOT, viewSpotId, "2");				
				}
			}
			if(StringUtils.isBlank(imgId3)){//新增或者删除
				if(upload3 != null){
					boolean uploadResult = uploadFile(folderName + "\\" + viewSpotId, "3", upload3, upload3FileName);
					if(uploadResult){
						String extFileName = upload3FileName.substring(upload3FileName.lastIndexOf("."));	
						imgService.saveImageInf(IMAGE_TYPE.VIEWSPOT, viewSpotId, "3", extFileName, serverUrl, getCurrentUser());
					} 
				} else {
					imgService.deleteByName(IMAGE_TYPE.VIEWSPOT, viewSpotId, "3");				
				}
			}
		} else if (StringUtils.equals(uploadBizType, ITEM_IMAGE)) {
			String itemId = request.getParameter("itemId");
			ItemInf item = itemService.getItemById(Long.valueOf(itemId));
			if(item.getType().intValue() == ITEM_TYPE.PUBLIC.getValue() && isTravelUser()){
				JsonUtils.write(response, "{\"code\":\"-2\",\"msg\":\"\"}");
				return null;
			}
			String folderName = "item";
			if(StringUtils.isBlank(imgId1)){//新增或者删除
				if(upload1 != null){
					boolean uploadResult = uploadFile(folderName + "\\" + itemId, "1", upload1, upload1FileName);
					if(uploadResult){
						String extFileName = upload1FileName.substring(upload1FileName.lastIndexOf("."));	
						imgService.saveImageInf(IMAGE_TYPE.ITEM, itemId, "1", extFileName, serverUrl, getCurrentUser());
					}
				} else {
					imgService.deleteByName(IMAGE_TYPE.ITEM, itemId, "1");
				}
			}
			if(StringUtils.isBlank(imgId2)){//新增或者删除
				if(upload2 != null){
					boolean uploadResult = uploadFile(folderName + "\\" + itemId, "2", upload2, upload2FileName);
					if(uploadResult){
						String extFileName = upload2FileName.substring(upload2FileName.lastIndexOf("."));	
						imgService.saveImageInf(IMAGE_TYPE.ITEM, itemId, "2", extFileName, serverUrl, getCurrentUser());
					} 
				} else {
					imgService.deleteByName(IMAGE_TYPE.ITEM, itemId, "2");				
				}
			}
			if(StringUtils.isBlank(imgId3)){//新增或者删除
				if(upload3 != null){
					boolean uploadResult = uploadFile(folderName + "\\" + itemId, "3", upload3, upload3FileName);
					if(uploadResult){
						String extFileName = upload3FileName.substring(upload3FileName.lastIndexOf("."));	
						imgService.saveImageInf(IMAGE_TYPE.ITEM, itemId, "3", extFileName, serverUrl, getCurrentUser());
					} 
				} else {
					imgService.deleteByName(IMAGE_TYPE.ITEM, itemId, "3");				
				}
			}
		}
		if (result == 0) {
			if (errorList.size() > 0) {
				JsonUtils.write(response, "{\"code\":\"-1\",\"msg\":\""
						+ errorList.toString() + "\"}");
			} else {
				JsonUtils.write(response,
						"{\"code\":\"0\",\"msg\":\"success\"}");
			}
		} else if (result == 1) {
			JsonUtils.write(response, "{\"code\":\"-1\",\"msg\":\""
					+ errorList.toString() + "\"}");
		}
		return null;
	}

	/**
	 * @param string
	 * @param upload12
	 * @param upload1FileName2
	 * @param string2
	 * @return
	 */
	private boolean uploadFile(String targetFolder, String targetFileName, File uploadFile,
			String uploadFileName) {
		String dstPath = getRealPath() + "\\images" + "\\" + targetFolder;
		String extFileName = uploadFileName.substring(uploadFileName.lastIndexOf("."));					
		boolean uploadResult = fileService.upload(uploadFile, dstPath, targetFileName + extFileName);
		return uploadResult;
	}

	public File getUpload1() {
		return upload1;
	}

	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}

	public File getUpload2() {
		return upload2;
	}

	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}

	public File getUpload3() {
		return upload3;
	}

	public void setUpload3(File upload3) {
		this.upload3 = upload3;
	}

	public String getUploadBizType() {
		return uploadBizType;
	}

	public void setUploadBizType(String uploadBizType) {
		this.uploadBizType = uploadBizType;
	}

	public String getUpload1FileName() {
		return upload1FileName;
	}

	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}

	public String getImgId1() {
		return imgId1;
	}

	public String getUpload2FileName() {
		return upload2FileName;
	}

	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}

	public String getUpload3FileName() {
		return upload3FileName;
	}

	public void setUpload3FileName(String upload3FileName) {
		this.upload3FileName = upload3FileName;
	}

	public void setImgId1(String imgId1) {
		this.imgId1 = imgId1;
	}

	public String getImgId2() {
		return imgId2;
	}

	public void setImgId2(String imgId2) {
		this.imgId2 = imgId2;
	}

	public String getImgId3() {
		return imgId3;
	}

	public void setImgId3(String imgId3) {
		this.imgId3 = imgId3;
	}
	
}
