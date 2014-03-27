package com.travel.entity;
// default package

import com.travel.entity.MemberInf;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * MemberPrivate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="member_private"
    ,catalog="travel_love_db"
)
public class MemberPrivate extends AbstractMemberPrivate implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public MemberPrivate() {
    }

    
    /** full constructor */
    public MemberPrivate(MemberInf memberInfByVisibleMemberId, MemberInf memberInfByMemberId, Integer type, Integer visibility) {
        super(memberInfByVisibleMemberId, memberInfByMemberId, type, visibility);        
    }
   
}
