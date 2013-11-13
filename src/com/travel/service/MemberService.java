package com.travel.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.entity.LocationLog;
import com.travel.entity.MemberInf;
import com.travel.entity.TeamInfo;

@Service
public class MemberService
{
	@Autowired
	private MemberInfDAO memberInfDao;	
	@Autowired
	private LocationLogDAO locationDao;	
	
	
	public MemberInf getMemberById(Long id){
		return memberInfDao.findById(id);
	}


	/**
	 * @param member
	 * @return
	 */
	public int updateMember(MemberInf member) {
		member.setUpdateDate(new Timestamp(new Date().getTime()));
		return memberInfDao.updateMember(member);
	}


	/**
	 * @param member
	 * @param longitude
	 * @param latitude
	 */
	public void updateMemberLocation(MemberInf member, Double longitude,
			Double latitude) {
		LocationLog location = locationDao.getLocationByMember(member.getTeamInfo().getId(), member.getId());
		if(location != null && location.getId() > 0){
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			location.setLocateTime(new Timestamp(System.currentTimeMillis()));
			location.setUpdateDate(location.getLocateTime());
			locationDao.update(location);
		} else {
			location = new LocationLog();
			location.setMemberInf(member);
			TeamInfo teamInfo = new TeamInfo();
			teamInfo.setId(member.getTeamInfo().getId());
			location.setTeamInfo(teamInfo);
			location.setCreateDate(new Timestamp(System.currentTimeMillis()));
			location.setUpdateDate(location.getCreateDate());
			location.setLocateTime(location.getCreateDate());
			location.setLongitude(longitude);
			location.setLatitude(latitude);
			locationDao.save(location);
		}
		
	}


	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public MemberInf getMemberByCredentials(Long teamId, String mobile, String password) {
		MemberInf member = memberInfDao.findByCredentials(teamId, mobile, password);
		return member;
	}


	/**
	 * @param dto
	 * @return
	 */
	public int getTotalMemberNum(SearchMemberDTO dto) {
		return memberInfDao.getTotalNum(dto);
	}


	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<MemberInf> findMembers(SearchMemberDTO dto, PageInfoDTO pageInfo) {
		return memberInfDao.findMembers(dto, pageInfo);
	}


	/**
	 * @param memberInf
	 * @return
	 */
	public int addMember(MemberInf memberInf) {
		memberInf.setCreateDate(new Timestamp(new Date().getTime()));
		memberInf.setUpdateDate(memberInf.getCreateDate());
		return memberInfDao.save(memberInf);
	}


	/**
	 * @param ids
	 */
	public void deleteMemberByIds(String ids) {
		memberInfDao.deleteByIds(ids);
		
	}
}
