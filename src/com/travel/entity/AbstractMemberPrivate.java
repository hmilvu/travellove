package com.travel.entity;
// default package

import com.travel.entity.MemberInf;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;


/**
 * AbstractMemberPrivate entity provides the base persistence definition of the MemberPrivate entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass

public abstract class AbstractMemberPrivate extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Long id;
     private MemberInf memberInfByVisibleMemberId;
     private MemberInf memberInfByMemberId;
     private Integer type;
     private Integer visibility;


    // Constructors

    /** default constructor */
    public AbstractMemberPrivate() {
    }

    
    /** full constructor */
    public AbstractMemberPrivate(MemberInf memberInfByVisibleMemberId, MemberInf memberInfByMemberId, Integer type, Integer visibility) {
        this.memberInfByVisibleMemberId = memberInfByVisibleMemberId;
        this.memberInfByMemberId = memberInfByMemberId;
        this.type = type;
        this.visibility = visibility;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="increment")@Id @GeneratedValue(generator="generator")
    
    @Column(name="id", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="visible_member_id", nullable=false)

    public MemberInf getMemberInfByVisibleMemberId() {
        return this.memberInfByVisibleMemberId;
    }
    
    public void setMemberInfByVisibleMemberId(MemberInf memberInfByVisibleMemberId) {
        this.memberInfByVisibleMemberId = memberInfByVisibleMemberId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="member_id", nullable=false)

    public MemberInf getMemberInfByMemberId() {
        return this.memberInfByMemberId;
    }
    
    public void setMemberInfByMemberId(MemberInf memberInfByMemberId) {
        this.memberInfByMemberId = memberInfByMemberId;
    }
    
    @Column(name="type", nullable=false)

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name="visibility", nullable=false)

    public Integer getVisibility() {
        return this.visibility;
    }
    
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
   








}