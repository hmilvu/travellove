package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.admin.dto.SearchTravelDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.TeamInfoDAO;
import com.travel.dao.TravelInfDAO;
import com.travel.entity.TeamInfo;
import com.travel.entity.TravelInf;

@Service
public class TravelInfService
{
	@Autowired
	private TravelInfDAO travelDao;
	@Autowired
	private TeamInfoDAO teamDao;

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalRoleNum(SearchTravelDTO dto) {
		int totalNum = travelDao.getTotalNum(dto);
		return totalNum;
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<TravelInf> findTravels(SearchTravelDTO dto,
			PageInfoDTO pageInfo) {
		List<TravelInf> list = travelDao.findTravels(dto, pageInfo);
		return list;
	}

	/**
	 * @param name
	 * @return
	 */
	public List<TravelInf> getTravelInfByName(String name) {
		List<TravelInf> list = travelDao.findByName(name);
		return list;
	}

	/**
	 * @param travel
	 * @return
	 */
	public int addTravelInf(TravelInf travel) {
		travel.setCreateDate(new Timestamp(new Date().getTime()));
		travel.setUpdateDate(travel.getCreateDate());
		int result = travelDao.save(travel);
		return result;
	}

	/**
	 * @param idLong
	 * @return
	 */
	public List<TeamInfo> getTeamByTravelId(Long idLong) {
		return teamDao.findByTravelId(idLong);
	}

	/**
	 * @param idLong
	 */
	public void deleteTravelById(Long travelId) {
		travelDao.deleteById(travelId);		
	}

	/**
	 * @param idLong
	 * @return
	 */
	public TravelInf getTravelById(Long travelId) {
		return travelDao.findById(travelId);
	}

	/**
	 * @param travel
	 * @return
	 */
	public int updateTravel(TravelInf travel) {
		travel.setUpdateDate(new Timestamp(new Date().getTime()));		
		return travelDao.update(travel);
	}
}
