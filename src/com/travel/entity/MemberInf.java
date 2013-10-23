package com.travel.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.common.dto.MemberDTO;

/**
 * MemberInf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member_inf", catalog = "travel_love_db")
public class MemberInf extends AbstractMemberInf implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public MemberInf() {
	}

	/** minimal constructor */
	public MemberInf(TeamInfo teamInfo, SysUser sysUser, String memberName,
			String nickname, String travelerMobile, Integer memberType,
			String password, Integer sex, Integer age, String idNo,
			Timestamp createDate, Timestamp updateDate) {
		super(teamInfo, sysUser, memberName, nickname, travelerMobile,
				memberType, password, sex, age, idNo, createDate, updateDate);
	}

	/** full constructor */
	public MemberInf(TeamInfo teamInfo, SysUser sysUser, String memberName,
			String nickname, String travelerMobile, Integer memberType,
			String password, Integer sex, Integer age, Integer idType,
			String idNo, String avatarUrl, String profile, String interest,
			Timestamp createDate, Timestamp updateDate, Set<Reply> replies,
			Set<LocationLog> locationLogs, Set<Order> orders) {
		super(teamInfo, sysUser, memberName, nickname, travelerMobile,
				memberType, password, sex, age, idType, idNo, avatarUrl,
				profile, interest, createDate, updateDate, replies,
				locationLogs, orders);
	}

	/**
	 * @return
	 */
	public MemberDTO toDTO() {
		MemberDTO dto = new MemberDTO();
		dto.setId(getId());
		dto.setMemberName(getMemberName());
		dto.setNickname(getNickname());
		dto.setTravelerMobile(getTravelerMobile());
		dto.setMemberType(getMemberType());
		dto.setSex(getSex());
		dto.setAge(getAge());
		dto.setIdType(getIdType());
		dto.setIdNo(getIdNo());
		dto.setAvatarUrl(getAvatarUrl());
		dto.setProfile(getProfile());
		dto.setInterest(getInterest());
		return dto;
	}

}
