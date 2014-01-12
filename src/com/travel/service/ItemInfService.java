package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.ImgInfDAO;
import com.travel.dao.ItemInfDAO;
import com.travel.entity.ItemInf;

@Service
public class ItemInfService extends AbstractBaseService
{
	@Autowired
	private ItemInfDAO itemDao;
	@Autowired
	private ImgInfDAO imgDao;
	
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
	public List<ItemInfDTO> getItemList(Long travelId, PageInfoDTO pageInfo) {
		List<ItemInf> list = itemDao.findItems(travelId, pageInfo);
		List<ItemInfDTO> result = new ArrayList<ItemInfDTO>();
		for(ItemInf item : list){
			List<String> imageUrls = imgDao.findUrls(IMAGE_TYPE.ITEM, item.getId());
			item.setUrls(imageUrls);
			result.add(item.toDTO());
		}
		return result;
	}
}
