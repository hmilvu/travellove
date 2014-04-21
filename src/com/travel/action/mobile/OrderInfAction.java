/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.Constants.ORDER_STATUS;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.OrderDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.ItemInf;
import com.travel.entity.MemberInf;
import com.travel.entity.Order;
import com.travel.service.ItemInfService;
import com.travel.service.MemberService;
import com.travel.service.OrderService;

/**
 * @author Lenovo
 * 
 */
public class OrderInfAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ItemInfService itemService;
	@Autowired
	private OrderService orderService;
	
	public void search() {
		String data = getMobileData();
		Object id = getMobileParameter(data, "memberId");
		Object pageSize = getMobileParameter(data, "pageSize");
		Object pageNumber = getMobileParameter(data, "pageNumber");
		Long idLong = Long.valueOf(0);
		PageInfoDTO pageInfo = new PageInfoDTO();
		try {
			idLong = Long.valueOf(id.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		MemberInf member = memberService.getMemberById(idLong);
		if (member != null && member.getId() > 0) {
			List<OrderDTO> list = orderService.getOrdersByMemberId(idLong, pageInfo);
			SuccessResult<List<OrderDTO>> result = new SuccessResult<List<OrderDTO>>(list);
			sendToMobile(result);
		} else {
			FailureResult result = new FailureResult("该用户不存在");
			sendToMobile(result);
		}
	}
	
	public void detail(){
		String data = getMobileData();
		Object orderId = getMobileParameter(data, "orderId");
		Long orderIdLong = Long.valueOf(0);
		try {
			orderIdLong = Long.valueOf(orderId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("orderId类型错误");
			sendToMobile(result);
			return;
		}
		Order o = orderService.getOrderById(orderIdLong);
		if(o != null){
			SuccessResult<OrderDTO> result = new SuccessResult<OrderDTO>(o.toDTO());
			sendToMobile(result);
		} else{
			FailureResult result = new FailureResult("订单不存在orderId=" + orderId);
			sendToMobile(result);
		}
		return;
	}
	
	public void create(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		Object itemId = getMobileParameter(data, "itemId");
		Long itemIdLong = Long.valueOf(0);
		try {
			itemIdLong = Long.valueOf(itemId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemId类型错误");
			sendToMobile(result);
			return;
		}
		Object itemCount = getMobileParameter(data, "itemCount");
		Integer itemCountI = Integer.valueOf(0);
		try {
			itemCountI = Integer.valueOf(itemCount.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemCount类型错误");
			sendToMobile(result);
			return;
		}
		Object contactTel = getMobileParameter(data, "contactTel");
		if(contactTel == null || StringUtils.isBlank(contactTel.toString())){
			FailureResult result = new FailureResult("contactTel不能为空");
			sendToMobile(result);
			return;
		}
		Object remarkObj = getMobileParameter(data, "remark");
		String remark = remarkObj == null ? "" : remarkObj.toString();
		Object contactNameObj = getMobileParameter(data, "contactName");
		String contactName = contactNameObj == null ? "" : contactNameObj.toString();		
		MemberInf member = memberService.getMemberById(memberIdLong);
		ItemInf item = itemService.getItemById(itemIdLong);
		if(member == null){
			FailureResult result = new FailureResult("会员不存在memberId=" + memberId);
			sendToMobile(result);
			return;
		}
		if(item == null){
			FailureResult result = new FailureResult("产品不存在itemId=" + itemId);
			sendToMobile(result);
			return;
		}
		orderService.addOrder(member, item, itemCountI, contactTel.toString(), remark, contactName);
		SuccessResult<String> result = new SuccessResult<String>("Success");
		sendToMobile(result);
	}
	
	public void update(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		Object orderId = getMobileParameter(data, "orderId");
		Long orderIdLong = Long.valueOf(0);
		try {
			orderIdLong = Long.valueOf(orderId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("orderId类型错误");
			sendToMobile(result);
			return;
		}
		
		Object itemCount = getMobileParameter(data, "itemCount");
		Integer itemCountI = Integer.valueOf(0);
		try {
			itemCountI = Integer.valueOf(itemCount.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("itemCount类型错误");
			sendToMobile(result);
			return;
		}
		Object contactTel = getMobileParameter(data, "contactTel");
		if(contactTel == null || StringUtils.isBlank(contactTel.toString())){
			FailureResult result = new FailureResult("contactTel不能为空");
			sendToMobile(result);
			return;
		}
		Object remarkObj = getMobileParameter(data, "remark");
		String remark = remarkObj == null ? "" : remarkObj.toString();
		Object contactNameObj = getMobileParameter(data, "contactName");
		String contactName = contactNameObj == null ? "" : contactNameObj.toString();
		Order o = orderService.getOrderById(orderIdLong);
		if(o == null){			
			FailureResult result = new FailureResult("订单不存在orderId=" + orderId);
			sendToMobile(result);
			return;
		}
		if(memberIdLong.longValue() != o.getMemberInf().getId()){
			FailureResult result = new FailureResult("此订单不是由该会员订购，不能修改");
			sendToMobile(result);
			return;
		}
		orderService.updateOrder(o, itemCountI, contactTel.toString(), remark, contactName);
		SuccessResult<String> result = new SuccessResult<String>("Success");
		sendToMobile(result);
		return;
	} 
	
	public void delete(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long memberIdLong = Long.valueOf(0);
		try {
			memberIdLong = Long.valueOf(memberId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("memberId类型错误");
			sendToMobile(result);
			return;
		}
		Object orderId = getMobileParameter(data, "orderId");
		Long orderIdLong = Long.valueOf(0);
		try {
			orderIdLong = Long.valueOf(orderId.toString());
		} catch (Exception e) {
			FailureResult result = new FailureResult("orderId类型错误");
			sendToMobile(result);
			return;
		}
		
		Order o = orderService.getOrderById(orderIdLong);
		if(o == null){			
			FailureResult result = new FailureResult("订单不存在orderId=" + orderId);
			sendToMobile(result);
			return;
		}
		if(memberIdLong.longValue() != o.getMemberInf().getId()){
			FailureResult result = new FailureResult("此订单不是由该会员订购，不能删除");
			sendToMobile(result);
			return;
		}
		orderService.deleteOrderByIds(o.getId()+"");
		SuccessResult<String> result = new SuccessResult<String>("Success");
		sendToMobile(result);
		return;
	} 
	
}
