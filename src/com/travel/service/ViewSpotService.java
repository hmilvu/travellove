package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.dao.ImgInfDAO;
import com.travel.dao.ViewSpotInfoDAO;
import com.travel.entity.ViewSpotInfo;

@Service
public class ViewSpotService extends AbstractBaseService
{
	@Autowired
	private ViewSpotInfoDAO viewSpotDao;
	@Autowired
	private ImgInfDAO imageDao;
	
	public ViewSpotInfo getViewSpotById(Long id){
		ViewSpotInfo viewSpot = viewSpotDao.findById(id);
		List<String>urls = imageDao.findUrls(IMAGE_TYPE.VIEWSPOT, id);
		if(urls != null && urls.size() > 0){
			viewSpot.setUrls(urls);
		}
		return viewSpot;
	}


	/**
	 * @param dto
	 * @return
	 */
	public int getTotalViewSpotNum(SearchViewSpotDTO dto) {
		return viewSpotDao.getTotalNum(dto);
	}


	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<ViewSpotInfo> findViewSpots(SearchViewSpotDTO dto,
			PageInfoDTO pageInfo) {
		return viewSpotDao.findViewSpots(dto, pageInfo);
	}


	/**
	 * @param view
	 * @return
	 */
	public int addViewSpot(ViewSpotInfo view) {
		view.setCreateDate(new Timestamp(new Date().getTime()));
		view.setUpdateDate(view.getCreateDate());
		return viewSpotDao.save(view);
	}


	/**
	 * @param ids
	 */
	public void deleteViewSpotByIds(String ids) {
		viewSpotDao.deleteByIds(ids);
		
	}


	/**
	 * @param view
	 * @return
	 */
	public int updateViewSpot(ViewSpotInfo view) {
		view.setUpdateDate(new Timestamp(new Date().getTime()));
		return viewSpotDao.update(view);
	}


	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<ViewSpotDTO> findViewSpotsDTO(SearchViewSpotDTO dto,
			PageInfoDTO pageInfo) {
		List<ViewSpotInfo> list = viewSpotDao.findViewSpots(dto, pageInfo);
		List<ViewSpotDTO> resultList = new ArrayList<ViewSpotDTO>();
		for(ViewSpotInfo view : list){
			resultList.add(view.toDTO());
		}
		return resultList;
	}
	
}
