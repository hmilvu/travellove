/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.Constants.MEMBER_STATUS;
import com.travel.common.Constants.TEAM_STATUS;
import com.travel.common.admin.dto.SearchMemberDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.MemberInf;
import com.travel.entity.TeamInfo;
import com.travel.service.MemberService;
import com.travel.service.TeamInfoService;
import com.travel.utils.DateUtils;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class MemberInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MemberService memberService;

	public String list(){
		String name = request.getParameter("name");
		String teamName = request.getParameter("teamName");
		String phoneNumber = request.getParameter("phoneNumber");
		String idNumber = request.getParameter("idNumber");
		String memberType = request.getParameter("memberType");
		String pageSize = request.getParameter("numPerPage");
		String pageNumber = request.getParameter("pageNum");
		String teamId = request.getParameter("teamId");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageNumber(1);
		}
		try{			
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){	
			pageInfo.setPageSize(Constants.ADMIN_DEFAULT_PAGE_SIZE);
		}
		SearchMemberDTO dto = new SearchMemberDTO();		
		dto.setTeamName(teamName);
		dto.setName(name);
		if(StringUtils.isNotBlank(teamId)){
			dto.setTeamId(Long.valueOf(teamId));
		}
		dto.setIdNumber(idNumber);
		dto.setPhoneNumber(phoneNumber);
		if(StringUtils.isNotBlank(memberType)){
			dto.setMemberType(Integer.valueOf(memberType));
		}
		dto.setTravelId(getCurrentUser().getTravelInf().getId());
		int totalNum = memberService.getTotalMemberNum(dto);
		List<MemberInf> list = memberService.findMembers(dto, pageInfo);
		request.setAttribute("memberList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("name", name);
		if(StringUtils.isNotBlank(memberType)){
			request.setAttribute("memberType", Integer.valueOf(memberType));
		}
		request.setAttribute("teamName", teamName);
		request.setAttribute("phoneNumber", phoneNumber);
		request.setAttribute("idNumber", idNumber);
		request.setAttribute("teamId", teamId);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String add(){
		return "add";
	}
	
	public void create(){
		String teamId = request.getParameter("teamLookup.id");
		String name = request.getParameter("name");
		String memberType = request.getParameter("memberType");
		String nickname = request.getParameter("nickname");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");		
		String sex = request.getParameter("sex");	
		String age = request.getParameter("age");	
		String idType = request.getParameter("idType");	
		String idNo = request.getParameter("idNo");	
		String interest = request.getParameter("interest");	
		String profile = request.getParameter("profile");	
		
		TeamInfo team = new TeamInfo();
		team.setId(Long.valueOf(teamId));
		MemberInf memberInf = new MemberInf();
		memberInf.setMemberName(name);
		memberInf.setMemberType(Integer.valueOf(memberType));
		memberInf.setNickname(nickname);
		memberInf.setTravelerMobile(phoneNumber);
		memberInf.setPassword(password);
		memberInf.setSex(Integer.valueOf(sex));
		memberInf.setAge(Integer.valueOf(age));
		memberInf.setIdType(Integer.valueOf(idType));
		memberInf.setIdNo(idNo);
		memberInf.setInterest(interest);
		memberInf.setProfile(profile);
		memberInf.setTeamInfo(team);
		memberInf.setStatus(MEMBER_STATUS.ACTIVE.getValue());
		memberInf.setSysUser(getCurrentUser());
		if(memberService.addMember(memberInf) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
	}
	
	public void delete(){
		String ids = request.getParameter("ids");
		memberService.deleteMemberByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"团员管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		MemberInf memberInf = memberService.getMemberById(idLong);	
		if(memberInf != null && memberInf.getId() > 0){
			request.setAttribute("editMember", memberInf);
		}
		return "edit";
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		String id = request.getParameter("memberId");		
		String teamId = request.getParameter("teamLookup.id");
		String name = request.getParameter("memberName");
		String memberType = request.getParameter("memberType");
		String nickname = request.getParameter("nickname");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");		
		String sex = request.getParameter("sex");	
		String age = request.getParameter("age");	
		String idType = request.getParameter("idType");	
		String idNo = request.getParameter("idNo");	
		String interest = request.getParameter("interest");	
		String profile = request.getParameter("profile");	
		
		MemberInf memberInf = memberService.getMemberById(Long.valueOf(id));
		memberInf.setMemberName(name);
		memberInf.setMemberType(Integer.valueOf(memberType));
		memberInf.setNickname(nickname);
		memberInf.setTravelerMobile(phoneNumber);
		memberInf.setPassword(password);
		memberInf.setSex(Integer.valueOf(sex));
		memberInf.setAge(Integer.valueOf(age));
		memberInf.setIdType(Integer.valueOf(idType));
		memberInf.setIdNo(idNo);
		memberInf.setInterest(interest);
		memberInf.setProfile(profile);
		if(StringUtils.isNotBlank(teamId)){
			TeamInfo team = new TeamInfo();
			team.setId(Long.valueOf(teamId));			
			memberInf.setTeamInfo(team);
		}
		if(memberService.updateMember(memberInf) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));
		}
		
	}
	
	public String assign(){
		list();
		return "teamlist";
	}
	
	public void upload(){
		String teamId = request.getParameter("teamId");
		System.out.println("teamId = " + teamId);
		JsonUtils.write(response, "{\"id\":\"1000\",\"fileName\":\"测试文件.txt\"}");
		
	}
}
