package com.travel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.Constants.MEMBER_TYPE;
import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.entity.LocationLog;
import com.travel.entity.MemberInf;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;

@Service
public class MemberService extends AbstractBaseService
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


	/**
	 * @param idArray
	 * @return
	 */
	public List<Object[]> getMemberForQrCode(String[] idArray) {
		List<Long>idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		return memberInfDao.findForQrCode(idList);
	}


	/**
	 * @param teamId
	 * @param upload
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws BiffException 
	 */
	public List<Integer> importMembers(String teamId, File upload, SysUser createUser) throws Throwable {
		List<Integer> errorList = new ArrayList<Integer>();
		Workbook book = Workbook.getWorkbook(new FileInputStream(upload));
		Sheet[] allSheet = book.getSheets();
		Sheet memberSheet = allSheet[0];
		int nSheetCount = memberSheet.getRows();	
        for (int i = 2; i < nSheetCount; i++) {
            // 获得一行的所有单元格
            Cell[] row = memberSheet.getRow(i);
            String typeStr = row[0].getContents();  
            Integer type = MEMBER_TYPE.TRAVELER.getValue();//默认为游客
            try{
            	type = Integer.valueOf(typeStr);
            } catch(Throwable ignore){}
            
            String name = row[1].getContents();
            String nickname = row[2].getContents();
            String phoneNumber = row[3].getContents();
            String password = row[4].getContents();
            String genderStr = row[5].getContents();
            Integer gender = 2;
            try{
            	gender = Integer.valueOf(genderStr);
            } catch(Throwable ignore){}
            String ageStr = row[6].getContents();
            Integer age = 0;
            try{
            	age = Double.valueOf(ageStr).intValue();
            } catch(Throwable ignore){}
            String idTypeStr = row[7].getContents();
            Integer idType = 0;
            try{
            	idType = Integer.valueOf(idTypeStr);
            } catch(Throwable ignore){}
            String idNumber = row[8].getContents();
            if(StringUtils.isBlank(name) || StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(idNumber)){
            	errorList.add(Integer.valueOf(i+1));
            	continue;
            }
            String interest = row[9].getContents();
            String profile = row[10].getContents();

            TeamInfo team = new TeamInfo();
    		team.setId(Long.valueOf(teamId));
    		MemberInf memberInf = new MemberInf();
    		memberInf.setMemberName(name);
    		memberInf.setMemberType(type);
    		memberInf.setNickname(nickname);
    		memberInf.setTravelerMobile(phoneNumber);
    		memberInf.setPassword(password);
    		memberInf.setSex(gender);
    		memberInf.setAge(age);
    		memberInf.setIdType(Integer.valueOf(idType));
    		memberInf.setIdNo(idNumber);
    		memberInf.setInterest(interest);
    		memberInf.setProfile(profile);
    		memberInf.setTeamInfo(team);
    		memberInf.setStatus(MEMBER_STATUS.ACTIVE.getValue());
    		memberInf.setSysUser(createUser);
    		try{
	    		if(addMember(memberInf) != 0){
	    			errorList.add(Integer.valueOf(i));
	    		}
    		}catch(Throwable e){
    			errorList.add(Integer.valueOf(i+1));
    		}            
        }	
        return errorList;
	}


	/**
	 * @param idList
	 * @return
	 */
	public List<MemberInf> findAllMembersByTeamIds(List<Long> idList) {
		return memberInfDao.findByTeamIds(idList);
	}
}
