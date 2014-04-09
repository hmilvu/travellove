package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ViewSpotDTO;
import com.travel.dao.ImgInfDAO;
import com.travel.dao.ItemInfDAO;
import com.travel.dao.MessageDAO;
import com.travel.entity.ItemInf;

@Service
public class ItemInfService extends AbstractBaseService
{
	@Autowired
	private ItemInfDAO itemDao;
	@Autowired
	private ImgInfDAO imgDao;
	@Autowired
	private MessageDAO messageDAO;
	public ItemInf getItemById(Long id){
		return itemDao.findById(id);
	}


	/**
	 * @param member
	 * @return
	 */
	public int updateItem(ItemInf item) {
		item.setUpdateDate(new Timestamp(new Date().getTime()));
		return itemDao.update(item);
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalItemNum(SearchItemDTO dto) {
		return itemDao.getTotalNum(dto);
	}


	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<ItemInf> findItems(SearchItemDTO dto, PageInfoDTO pageInfo) {
		return itemDao.findItems(dto, pageInfo);
	}


	/**
	 * @param memberInf
	 * @return
	 */
	public int addItem(ItemInf item) {
		item.setCreateDate(new Timestamp(new Date().getTime()));
		item.setUpdateDate(item.getCreateDate());
		return itemDao.save(item);
	}


	/**
	 * @param ids
	 */
	public void deleteItemByIds(String ids) {
		itemDao.deleteByIds(ids);
		
	}


	/**
	 * @param pageInfo
	 * @return
	 */
	public List<ItemInfDTO> getItemList(SearchItemDTO dto, PageInfoDTO pageInfo) {
		List<ItemInf> list = itemDao.findItemList(dto, pageInfo);
		List<ItemInfDTO> result = new ArrayList<ItemInfDTO>();
		for(ItemInf item : list){
			int commentCount = messageDAO.getTotalMessageNum(MESSAGE_RECEIVER_TYPE.ITEM, item.getId());
			item.setCommentCount(commentCount);
			List<String> imageUrls = imgDao.findUrls(IMAGE_TYPE.ITEM, item.getId());
			item.setUrls(imageUrls);
			double score = messageDAO.caculateScore(MESSAGE_RECEIVER_TYPE.ITEM, item.getId());
			ItemInfDTO itemDto = item.toDTO();
			itemDto.setScore(score);
			result.add(itemDto);
		}
		return result;
	}


	/**
	 * @param id
	 * @return
	 */
	public List<ItemInfDTO> getItemByViewSpotId(Long travelId, Long viewSpotId) {
		List<ItemInf> list = itemDao.findByViewSpotId(travelId, viewSpotId);
		List<ItemInfDTO> result = new ArrayList<ItemInfDTO>();
		for(ItemInf item : list){
			int commentCount = messageDAO.getTotalMessageNum(MESSAGE_RECEIVER_TYPE.ITEM, item.getId());
			item.setCommentCount(commentCount);
			List<String> imageUrls = imgDao.findUrls(IMAGE_TYPE.ITEM, item.getId());
			item.setUrls(imageUrls);
			double score = messageDAO.caculateScore(MESSAGE_RECEIVER_TYPE.ITEM, item.getId());
			ItemInfDTO itemDto = item.toDTO();
			itemDto.setScore(score);
			result.add(itemDto);
		}
		return result;
	}
}
