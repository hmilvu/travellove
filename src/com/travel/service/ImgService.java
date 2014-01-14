package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.dao.ImgInfDAO;
import com.travel.entity.ImgInf;
import com.travel.entity.SysUser;

@Service
public class ImgService extends AbstractBaseService
{
	@Autowired
	private ImgInfDAO imgDao;

	/**
	 * @param viewspot
	 * @param viewSpotId
	 * @param string
	 * @param extFileName
	 * @param currentUser
	 */
	public int saveImageInf(IMAGE_TYPE type, String associateId,
			String imgName, String extFileName, String serverUrl, SysUser currentUser) {
		ImgInf img = imgDao.findByName(type, Long.valueOf(associateId), imgName);
		if(img == null){
			img = new ImgInf();
			img.setType(type.getValue());
			img.setAssociateId(Long.valueOf(associateId));
			img.setImgName(imgName);
			img.setCreateDate(new Timestamp(new Date().getTime()));
			img.setSysUser(currentUser);
			img.setSize(0);
		}
		img.setSuffix(extFileName);
		String url = null;
		switch (type){
		case AVATAR:
			url = serverUrl + "/images/avatar/" + associateId + "/" + imgName + extFileName;
			break;
		case VIEWSPOT:
			url = serverUrl + "/images/viewspot/" + associateId + "/" + imgName + extFileName;
			break;
		case ITEM:
			url = serverUrl + "/images/item/" + associateId + "/" + imgName + extFileName;
			break;
		default:
			return 1;
		}
		img.setUrl(url);
		img.setUpdateDate(img.getCreateDate());
		int result = imgDao.saveOrUpdate(img);
		return result;
	}

	/**
	 * @param viewspot
	 * @param id
	 * @return
	 */
	public List<ImgInf> getImageList(IMAGE_TYPE imageType, Long associateId) {
		List<ImgInf> imgList = imgDao.findImgInf(imageType, associateId);
		return imgList;
	}

	/**
	 * @param viewspot
	 * @param viewSpotId
	 * @param string
	 */
	public void deleteByName(IMAGE_TYPE type, String associateId,
			String name) {
		imgDao.deleteByName(type, associateId, name);
		
	}
}
