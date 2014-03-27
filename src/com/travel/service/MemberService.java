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
import com.travel.common.Constants.VISIBLITY;
import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.MemberDTO;
import com.travel.common.dto.MemberPrivateDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.LocationLogDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.dao.MemberPrivateDAO;
import com.travel.entity.LocationLog;
import com.travel.entity.MemberInf;
import com.travel.entity.MemberPrivate;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;

@Service
public class MemberService extends AbstractBaseService
{
	@Autowired
	private MemberInfDAO memberInfDao;	
	@Autowired
	private LocationLogDAO locationDao;	
	@Autowired
	private MemberPrivateDAO memberPrivateDao;
	
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
	public void saveMemberLocation(MemberInf member, Double longitude,
			Double latitude) {
		if(longitude > 1 && latitude > 1){
			LocationLog location = locationDao.getLocationByMember(member.getTeamInfo().getId(), member.getId());
	//		if(location != null && location.getId() > 0){
	//			location.setLatitude(latitude);
	//			location.setLongitude(longitude);
	//			location.setLocateTime(new Timestamp(System.currentTimeMillis()));
	//			location.setUpdateDate(location.getLocateTime());
	//			locationDao.update(location);
	//		} else {
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
	//		}	
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
            String typeStr = getString(row, 0);
            Integer type = MEMBER_TYPE.TRAVELER.getValue();//默认为游客
            try{
            	type = Integer.valueOf(typeStr);
            } catch(Throwable ignore){}
            
            String name = getString(row, 1);
            String nickname = getString(row, 2);
            String phoneNumber = getString(row, 3);
            String password = getString(row, 4);
            String genderStr = getString(row, 5);
            Integer gender = 2;
            try{
            	gender = Integer.valueOf(genderStr);
            } catch(Throwable ignore){}
            String ageStr = getString(row, 6);
            Integer age = 0;
            try{
            	age = Double.valueOf(ageStr).intValue();
            } catch(Throwable ignore){}
            String idTypeStr = getString(row, 7);
            Integer idType = 0;
            try{
            	idType = Integer.valueOf(idTypeStr);
            } catch(Throwable ignore){}
            String idNumber = getString(row, 8);
            if(StringUtils.isBlank(name) || StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(idNumber)){
            	errorList.add(Integer.valueOf(i+1));
            	continue;
            }
            String interest = getString(row, 9);
            String profile = getString(row, 10);

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
	 * @param cell
	 * @return
	 */
	private String getString(Cell[] row, int i) {
		if(i < row.length){
			if(row[i] == null){
				return "";
			} else {
				if(row[i].getContents() == null){
					return "";
				} else {
					return row[i].getContents();
				}
			}
		} else {
			return "";
		}
	}


	/**
	 * @param idList
	 * @return
	 */
	public List<MemberInf> findAllMembersByTeamIds(List<Long> idList) {
		return memberInfDao.findByTeamIds(idList);
	}


	/**
	 * @param memberIds
	 * @return
	 */
	public List<MemberInf> getMemberByIds(String memberIds) {
		String[]ids = StringUtils.split(memberIds, ",");
		List <Long>idList = new ArrayList<Long>();
		for(String id : ids){
			idList.add(Long.valueOf(id));
		}
		return memberInfDao.findByIds(idList);
	}


	/**
	 * @param memberIdLong
	 * @param teamIdLong
	 * @param type
	 * @return
	 */
	public MemberPrivateDTO getVisiableMember(Long memberId, Long teamId, int type) {
		List<Long> idList = new ArrayList<Long>();
		idList.add(Long.valueOf(teamId));
		List<MemberInf> memberList = memberInfDao.findByTeamIds(idList);
		List<Long> visibiltyList = memberInfDao.getVisibilityByType(memberId, type);
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		for(MemberInf m : memberList){
			list.add(m.toDTO());
		}
		MemberPrivateDTO dto = new MemberPrivateDTO();
		dto.setMemberList(list);
		dto.setVisibleMemberIdList(visibiltyList);
		return dto;
	}


	/**
	 * @param memberId
	 * @param string
	 */
	public void addMemberVisibility(Long memberId, String visibleMemberIds, int type) {
		String []visibleMemberArr = StringUtils.split(visibleMemberIds, ",");
		memberPrivateDao.deleteByMemberId(memberId, type);
		for(String visibleMemberId : visibleMemberArr){
			MemberInf m = new MemberInf();
			m.setId(memberId);
			
			MemberInf vm = new MemberInf();
			vm.setId(Long.valueOf(visibleMemberId.trim()));
			
			MemberPrivate p = new MemberPrivate();
			p.setMemberInfByMemberId(m);
			p.setMemberInfByVisibleMemberId(vm);
			p.setType(type);
			p.setVisibility(VISIBLITY.VISIBLE.getValue());
			memberPrivateDao.save(p);
		}
		
	}
}
