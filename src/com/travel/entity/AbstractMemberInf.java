package com.travel.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractMemberInf entity provides the base persistence definition of the
 * MemberInf entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractMemberInf extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private TeamInfo teamInfo;
	private SysUser sysUser;
	private String memberName;
	private String nickname;
	private String travelerMobile;
	private Integer memberType;
	private String password;
	private Integer sex;
	private Integer age;
	private Integer idType;
	private String idNo;
	private String avatarUrl;
	private String profile;
	private String interest;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<Reply> replies = new HashSet<Reply>(0);
	private Set<LocationLog> locationLogs = new HashSet<LocationLog>(0);
	private Set<Order> orders = new HashSet<Order>(0);

	// Constructors

	/** default constructor */
	public AbstractMemberInf() {
	}

	/** minimal constructor */
	public AbstractMemberInf(TeamInfo teamInfo, SysUser sysUser,
			String memberName, String nickname, String travelerMobile,
			Integer memberType, String password, Integer sex, Integer age,
			String idNo, Timestamp createDate, Timestamp updateDate) {
		this.teamInfo = teamInfo;
		this.sysUser = sysUser;
		this.memberName = memberName;
		this.nickname = nickname;
		this.travelerMobile = travelerMobile;
		this.memberType = memberType;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.idNo = idNo;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractMemberInf(TeamInfo teamInfo, SysUser sysUser,
			String memberName, String nickname, String travelerMobile,
			Integer memberType, String password, Integer sex, Integer age,
			Integer idType, String idNo, String avatarUrl, String profile,
			String interest, Timestamp createDate, Timestamp updateDate,
			Set<Reply> replies, Set<LocationLog> locationLogs, Set<Order> orders) {
		this.teamInfo = teamInfo;
		this.sysUser = sysUser;
		this.memberName = memberName;
		this.nickname = nickname;
		this.travelerMobile = travelerMobile;
		this.memberType = memberType;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.idType = idType;
		this.idNo = idNo;
		this.avatarUrl = avatarUrl;
		this.profile = profile;
		this.interest = interest;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.replies = replies;
		this.locationLogs = locationLogs;
		this.orders = orders;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", nullable = false)
	public TeamInfo getTeamInfo() {
		return this.teamInfo;
	}

	public void setTeamInfo(TeamInfo teamInfo) {
		this.teamInfo = teamInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "member_name", nullable = false, length=64)
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Column(name = "nickname", nullable = false, length = 64)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "traveler_mobile", nullable = false, length = 20)
	public String getTravelerMobile() {
		return this.travelerMobile;
	}

	public void setTravelerMobile(String travelerMobile) {
		this.travelerMobile = travelerMobile;
	}

	@Column(name = "member_type", nullable = false)
	public Integer getMemberType() {
		return this.memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	@Column(name = "password", nullable = false, length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "sex", nullable = false)
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "age", nullable = false)
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "id_type")
	public Integer getIdType() {
		return this.idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	@Column(name = "id_no", nullable = false, length = 32)
	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@Column(name = "avatar_url", length = 128)
	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Column(name = "profile", length = 1024)
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Column(name = "interest", length = 128)
	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	@Column(name = "create_date", nullable = false, length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_date", nullable = false, length = 19)
	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "memberInf")
	public Set<Reply> getReplies() {
		return this.replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "memberInf")
	public Set<LocationLog> getLocationLogs() {
		return this.locationLogs;
	}

	public void setLocationLogs(Set<LocationLog> locationLogs) {
		this.locationLogs = locationLogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "memberInf")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}