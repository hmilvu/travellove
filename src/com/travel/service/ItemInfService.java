package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.ItemInfDAO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.entity.ItemInf;
import com.travel.entity.LocationLog;
import com.travel.entity.MemberInf;
import com.travel.entity.TeamInfo;

@Service
public class ItemInfService extends AbstractBaseService
{
	@Autowired
	private MemberInfDAO memberInfDao;	
	@Autowired
	private LocationLogDAO locationDao;	
	@Autowired
	private ItemInfDAO itemDao;
	
	
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
}
