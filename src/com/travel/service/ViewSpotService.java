package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.action.admin.form.ItemInfForm;
import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.admin.dto.SearchViewSpotDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.dao.AreaDAO;
import com.travel.dao.ImgInfDAO;
import com.travel.dao.ItemInfDAO;
import com.travel.dao.RouteInfDAO;
import com.travel.dao.ViewSpotInfoDAO;
import com.travel.dao.ViewSpotItemDAO;
import com.travel.entity.AreaInf;
import com.travel.entity.ItemInf;
import com.travel.entity.TravelInf;
import com.travel.entity.ViewSpotInfo;
import com.travel.entity.ViewSpotItem;

@Service
public class ViewSpotService extends AbstractBaseService
{
	@Autowired
	private ViewSpotInfoDAO viewSpotDao;
	@Autowired
	private ImgInfDAO imageDao;
	@Autowired
	private AreaDAO areaDao;;
	@Autowired
	private RouteInfDAO routeDao;
	@Autowired
	private ItemInfDAO itemDao;
	@Autowired
	private ViewSpotItemDAO viewItemDao;
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
		List<ViewSpotInfo> list = viewSpotDao.findViewSpots(dto, pageInfo);
		for(ViewSpotInfo view : list){
			if(StringUtils.isNotBlank(view.getCity())){
				AreaInf city = areaDao.getByCode(view.getCity());
				view.setCityName(city.getCityName());
			}
			if(StringUtils.isNotBlank(view.getProvince())){
				AreaInf city = areaDao.getByCode(view.getProvince());
				view.setProvinceName(city.getCityName());
			}
		}
		return list;
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
		List<ViewSpotInfo> list = null;
		if(dto.getTeamId() == null){
			list = viewSpotDao.findViewSpots(dto, pageInfo);
		} else {
			List<Long> routeIdList = routeDao.getRouteIdsByTeamId(dto.getTeamId());		
			if(routeIdList.size() > 0){
				list = viewSpotDao.findTeamViewSpots(routeIdList, pageInfo);
			} else {
				list = new ArrayList<ViewSpotInfo>();
			}
		}
		List<ViewSpotDTO> resultList = new ArrayList<ViewSpotDTO>();
		for(ViewSpotInfo view : list){
			List<String>urls = imageDao.findUrls(IMAGE_TYPE.VIEWSPOT, view.getId());
			if(urls != null && urls.size() > 0){
				view.setUrls(urls);
			}
			if(StringUtils.isNotBlank(view.getCity())){
				AreaInf city = areaDao.getByCode(view.getCity());
				view.setCityName(city.getCityName());
			}
			if(StringUtils.isNotBlank(view.getProvince())){
				AreaInf city = areaDao.getByCode(view.getProvince());
				view.setProvinceName(city.getCityName());
			}
			resultList.add(view.toDTO());
		}
		return resultList;
	}


	/**
	 * @param id
	 * @param id2
	 * @return
	 */
	public List<ItemInf> getViewSpotItems(Long travelId, Long viewSpotId) {
		return itemDao.findByViewSpotId(travelId, viewSpotId);
	}


	/**
	 * @param id
	 * @param id2
	 * @param items
	 * @return
	 */
	public int updateItems(Long travelId, Long viewSpotId, List<ItemInfForm> items) {
		int result = -1;
		viewItemDao.deleteViewSpotItems(travelId, viewSpotId);
		if(items != null){
			for(ItemInfForm itemForm : items){
				TravelInf travelInf = new TravelInf();
				travelInf.setId(travelId);				
				ViewSpotInfo view = new ViewSpotInfo();
				view.setId(viewSpotId);				
				ItemInf itemInf = new ItemInf();
				itemInf.setId(itemForm.getViewItemForm().getId());
				
				ViewSpotItem item = new ViewSpotItem();
				item.setTravelInf(travelInf);
				item.setViewSpotInf(view);
				item.setItemInf(itemInf);
				item.setDisplayOrder(itemForm.getOrder());				
				viewItemDao.save(item);
			}
		}
		result = 0;
		return result;
	}
	
}
