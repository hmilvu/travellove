/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.travel.action.AuthorityAction;
import com.travel.common.Constants;
import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.admin.dto.SearchOrderDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.ImgInf;
import com.travel.entity.ItemInf;
import com.travel.entity.Order;
import com.travel.service.ImgService;
import com.travel.service.ItemInfService;
import com.travel.service.OrderService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class OrderAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	@Autowired
	private ItemInfService itemService;
	@Autowired
	private OrderService orderService;
	
	private String name;
	private String productName;
	private String teamName;	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String list(){
		String pageSize = request.getParameter("numPerPage");
		String pageNumber = request.getParameter("pageNum");
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
		SearchOrderDTO dto = new SearchOrderDTO();		
		dto.setProductName(productName);
		dto.setName(name);
		dto.setTeamName(teamName);
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = orderService.getTotalOrderNum(dto);
		List<Order> list = orderService.getOrders(dto, pageInfo);
		request.setAttribute("orderList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("name", name);
		request.setAttribute("brands", productName);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}	
	
	public void delete(){
		String ids = request.getParameter("ids");
		orderService.deleteOrderByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"订单管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
			
	}
}
