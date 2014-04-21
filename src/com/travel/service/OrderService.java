package com.travel.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.common.Constants.IMAGE_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.ORDER_STATUS;
import com.travel.common.admin.dto.SearchOrderDTO;
import com.travel.common.dto.ItemInfDTO;
import com.travel.common.dto.OrderDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.dao.ImgInfDAO;
import com.travel.dao.ItemInfDAO;
import com.travel.dao.MemberInfDAO;
import com.travel.dao.MessageDAO;
import com.travel.dao.OrderDAO;
import com.travel.dao.SysUserDAO;
import com.travel.entity.ItemInf;
import com.travel.entity.MemberInf;
import com.travel.entity.Order;
import com.travel.entity.SysUser;
import com.travel.entity.TeamInfo;

@Service
public class OrderService extends AbstractBaseService
{
	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private MemberInfDAO memberDao;
	@Autowired
	private SysUserDAO sysDao;
	@Autowired
	private ImgInfDAO imgDao;
	@Autowired
	private ItemInfDAO itemDao;
	@Autowired
	private MessageDAO messageDAO;
	/**
	 * @param ids
	 * @return
	 */
	public List<Order> getOrderByItemIds(String ids) {
		String []idArray = StringUtils.split(ids, ",");
		List <Long>idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		return orderDao.findByItemIds(idList);
		
	}

	/**
	 * @param dto
	 * @return
	 */
	public int getTotalOrderNum(SearchOrderDTO dto) {
		return orderDao.getTotalNum(dto);
	}

	/**
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public List<Order> getOrders(SearchOrderDTO dto, PageInfoDTO pageInfo) {
		 List<Order> list = orderDao.getOrders(dto, pageInfo);
		 for(Order o : list){
			 if(o.getCreateMemberInf() != null && o.getCreateMemberInf().getId() != null){
				 MemberInf memberInf = memberDao.findById(o.getCreateMemberInf().getId());
				 o.setOrderName(memberInf.getMemberName());
			 } else if(o.getSysUser() != null && o.getSysUser().getId() != null){
				 SysUser user = sysDao.findById(o.getSysUser().getId());
				 o.setOrderName("系统用户：" + user.getName());
			 }
		 }
		 return list;
	}

	/**
	 * @param ids
	 */
	public void deleteOrderByIds(String ids) {		
		orderDao.deleteByIds(ids);
		
	}

	/**
	 * @param idLong
	 * @param pageInfo
	 * @return
	 */
	public List<OrderDTO> getOrdersByMemberId(Long memberId, PageInfoDTO pageInfo) {
		List<Order> list = orderDao.getOrdersByMemberId(memberId, pageInfo);
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		for(Order o : list){
			ItemInf item = o.getItemInf();
			int commentCount = messageDAO.getTotalMessageNum(MESSAGE_RECEIVER_TYPE.ITEM, item.getId());
			item.setCommentCount(commentCount);
			List<String> imageUrls = imgDao.findUrls(IMAGE_TYPE.ITEM, item.getId());
			item.setUrls(imageUrls);
			double score = messageDAO.caculateScore(MESSAGE_RECEIVER_TYPE.ITEM, item.getId());
			ItemInfDTO itemDto = item.toDTO();
			itemDto.setScore(score);
			
			OrderDTO orderDto = o.toDTO();
			orderDto.setItem(itemDto);
			result.add(orderDto);
		}
		return result;
	}
	/**
	 * @param member
	 * @param item
	 * @param itemCount
	 */
	public void addOrder(MemberInf member, ItemInf item, Integer itemCount, String tel, String remark, String contactName) {
		Order o = new Order();
		o.setCreateDate(new Timestamp(new Date().getTime()));
		o.setUpdateDate(o.getCreateDate());
		o.setMemberInf(member);
		o.setItemInf(item);
		o.setItemCount(itemCount);
		o.setCreateMemberInf(member);
		o.setStatus(ORDER_STATUS.BOOKED.getValue());
		TeamInfo team = new TeamInfo();
		team.setId(member.getTeamInfo().getId());
		o.setTeamInfo(team);
		o.setTotalPrice(item.getPrice() * itemCount);
		o.setRemark(remark);
		o.setContactTel(tel);
		o.setContactName(contactName);
		orderDao.save(o);
	}

	/**
	 * @param orderIdLong
	 * @return
	 */
	public Order getOrderById(Long orderIdLong) {
		Order o = orderDao.findById(orderIdLong);
		return o;
	}

	/**
	 * @param itemCountI
	 * @param string
	 * @param remark
	 */
	public void updateOrder(Order order, Integer itemCountI, String contactTel, String remark, String contactName) {
		order.setContactTel(contactTel);
		order.setRemark(remark);
		order.setItemCount(itemCountI);
		order.setContactName(contactName);
		order.setUpdateDate(new Timestamp(new Date().getTime()));
		orderDao.update(order);
	}
	
}
