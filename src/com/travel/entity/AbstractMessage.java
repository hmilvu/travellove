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
 * AbstractMessage entity provides the base persistence definition of the
 * Message entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractMessage extends BaseEntity implements
		java.io.Serializable {

	// Fields

	private Long id;
	private TeamInfo teamInfo;
	private SysUser sysUser;
	private Long authorId;
	private Integer priority;
	private Integer status;
	private Integer type;
	private String topic;
	private String content;
	private Long receiverId;
	private Timestamp remindTime;
	private Integer remindMode;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Set<Reply> replies = new HashSet<Reply>(0);

	// Constructors

	/** default constructor */
	public AbstractMessage() {
	}

	/** minimal constructor */
	public AbstractMessage(TeamInfo teamInfo, SysUser sysUser, Long authorId,
			Integer priority, Integer status, Integer type, String topic,
			String content, Long receiverId, Timestamp remindTime,
			Integer remindMode, Timestamp createDate, Timestamp updateDate) {
		this.teamInfo = teamInfo;
		this.sysUser = sysUser;
		this.authorId = authorId;
		this.priority = priority;
		this.status = status;
		this.type = type;
		this.topic = topic;
		this.content = content;
		this.receiverId = receiverId;
		this.remindTime = remindTime;
		this.remindMode = remindMode;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public AbstractMessage(TeamInfo teamInfo, SysUser sysUser, Long authorId,
			Integer priority, Integer status, Integer type, String topic,
			String content, Long receiverId, Timestamp remindTime,
			Integer remindMode, Timestamp createDate, Timestamp updateDate,
			Set<Reply> replies) {
		this.teamInfo = teamInfo;
		this.sysUser = sysUser;
		this.authorId = authorId;
		this.priority = priority;
		this.status = status;
		this.type = type;
		this.topic = topic;
		this.content = content;
		this.receiverId = receiverId;
		this.remindTime = remindTime;
		this.remindMode = remindMode;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.replies = replies;
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

	@Column(name = "author_id", nullable = false)
	public Long getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	@Column(name = "priority", nullable = false)
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "topic", nullable = false, length = 64)
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Column(name = "content", nullable = false, length = 128)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "receiver_id", nullable = false)
	public Long getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	@Column(name = "remind_time", nullable = false, length = 19)
	public Timestamp getRemindTime() {
		return this.remindTime;
	}

	public void setRemindTime(Timestamp remindTime) {
		this.remindTime = remindTime;
	}

	@Column(name = "remind_mode", nullable = false)
	public Integer getRemindMode() {
		return this.remindMode;
	}

	public void setRemindMode(Integer remindMode) {
		this.remindMode = remindMode;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "message")
	public Set<Reply> getReplies() {
		return this.replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

}