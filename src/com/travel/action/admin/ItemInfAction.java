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
import com.travel.common.Constants.ITEM_TYPE;
import com.travel.common.Constants.MESSAGE_RECEIVER_TYPE;
import com.travel.common.Constants.VIEW_SPOT_TYPE;
import com.travel.common.admin.dto.SearchItemDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.entity.ImgInf;
import com.travel.entity.ItemInf;
import com.travel.entity.Message;
import com.travel.entity.Order;
import com.travel.entity.ViewSpotInfo;
import com.travel.service.ImgService;
import com.travel.service.ItemInfService;
import com.travel.service.MessageService;
import com.travel.service.OrderService;
import com.travel.utils.JsonUtils;

/**
 * @author Lenovo
 *
 */
public class ItemInfAction extends AuthorityAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String brands;
	private String specification;
	private double price;
	private String description;
	private String contactPhone;
	private String contactName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Autowired
	private ItemInfService itemService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ImgService imageServcie;
	@Autowired
	private MessageService messageService;
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
		SearchItemDTO dto = new SearchItemDTO();		
		dto.setBrands(brands);
		dto.setName(name);
		if(isTravelUser()){
			dto.setTravelId(getCurrentUser().getTravelInf().getId());
		}
		int totalNum = itemService.getTotalItemNum(dto);
		List<ItemInf> list = itemService.findItems(dto, pageInfo);
		request.setAttribute("itemList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("name", name);
		request.setAttribute("brands", brands);
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "list";
	}
	
	public String add(){
		return "add";
	}
	
	public void create(){
		ItemInf item = new ItemInf();
		item.setName(name);
		item.setBrands(brands);
		item.setSpecification(specification);
		item.setPrice(price);
		item.setDescription(description);
		item.setContactName(contactName);
		item.setContactPhone(contactPhone);
		item.setType(ITEM_TYPE.PRIVATE.getValue());
		if(isTravelUser()){
			item.setTravelInf(getCurrentUser().getTravelInf());
			item.setType(ITEM_TYPE.PRIVATE.getValue());
		} else {
			item.setType(ITEM_TYPE.PUBLIC.getValue());
		}
		item.setSysUser(getCurrentUser());
		if(itemService.addItem(item) == 0){
			JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
		} else {
			JsonUtils.write(response, binder.toJson("result", Action.ERROR));		
		}
	}
	
	public void delete(){
		String ids = request.getParameter("ids");
		List<Order> orderList = orderService.getOrderByItemIds(ids);
		if(orderList != null && orderList.size() > 0){
			JsonUtils.write(response, "{\"statusCode\":\"300\", \"message\":\"次销售品已出售，不能删除\", \"navTabId\":\"销售品管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
			return;
		}
		itemService.deleteItemByIds(ids);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"销售品管理\", \"forwardUrl\":\"\", \"callbackType\":\"\", \"rel\":\"\"}");
	}
	
	public String edit(){
		String id = request.getParameter("uid");
		Long idLong = 0L;
		try{
			idLong = Long.valueOf(id);
		}catch(Throwable ignore){	
			return "edit";
		}
		ItemInf itemInf = itemService.getItemById(idLong);	
		if(itemInf != null && itemInf.getId() > 0){
			request.setAttribute("editItem", itemInf);
		}
		return "edit";
	}
	
	@SuppressWarnings("static-access")
	public void update(){
		String id = request.getParameter("itemInfId");		
		ItemInf item = itemService.getItemById(Long.valueOf(id));
		if(item.getType().intValue() == ITEM_TYPE.PUBLIC.getValue() && isTravelUser()){
			// if the item is public and the user is traveler, update is create.
			ItemInf newItem = new ItemInf();
			newItem.setName(name);
			newItem.setBrands(brands);
			newItem.setSpecification(specification);
			newItem.setPrice(price);
			newItem.setDescription(description);
			newItem.setContactName(contactName);
			newItem.setContactPhone(contactPhone);
			newItem.setType(ITEM_TYPE.PRIVATE.getValue());
			newItem.setTravelInf(getCurrentUser().getTravelInf());
			newItem.setSysUser(getCurrentUser());
			if(itemService.addItem(newItem) == 0){
				JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
			} else {
				JsonUtils.write(response, binder.toJson("result", Action.ERROR));
			}		
		} else {
			item.setName(name);
			item.setBrands(brands);
			item.setSpecification(specification);
			item.setPrice(price);
			item.setDescription(description);
			item.setContactName(contactName);
			item.setContactPhone(contactPhone);
			if(itemService.updateItem(item) == 0){
				JsonUtils.write(response, binder.toJson("result", Action.SUCCESS));			
			} else {
				JsonUtils.write(response, binder.toJson("result", Action.ERROR));
			}					
		}
		return;
	}
	
	public String upload(){
		edit();
		ItemInf item = (ItemInf)request.getAttribute("editItem");
		List<ImgInf> list = imageServcie.getImageList(IMAGE_TYPE.ITEM, item.getId());
		for(ImgInf img : list){
			request.setAttribute("imgId" + img.getImgName(), img.getId());
			request.setAttribute("imageName" + img.getImgName(), "images/item/" + item.getId() + "/" +img.getImgName() + img.getSuffix());
		}
		return "upload";
	}
	public String message(){
		edit();
		String itemId = request.getParameter("uid");
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
		int totalNum = messageService.getTotalMessageNumByReceiverId(MESSAGE_RECEIVER_TYPE.ITEM, Long.valueOf(itemId));
		List<Message> list = messageService.findMessageByReceiverId(MESSAGE_RECEIVER_TYPE.ITEM, Long.valueOf(itemId), pageInfo);
		request.setAttribute("messageList", list);
		request.setAttribute("totalCount", totalNum+"");
		request.setAttribute("pageNumber", pageNumber == null ? 1 : pageNumber);
		request.setAttribute("startNum", (pageInfo.getPageNumber()-1)*pageInfo.getPageSize());
		return "message";
	}
	
	public void deleteMessage(){
		String id = request.getParameter("id");
		Message msg = messageService.getMessageById(Long.valueOf(id));
		messageService.deleteMessage(msg);
		JsonUtils.write(response, "{\"statusCode\":\"200\", \"message\":\"删除成功\", \"navTabId\":\"查看评论\", \"forwardUrl\":\"\", \"callbackType\":\"closeCurrent\", \"rel\":\"\"}");
	
	}
}
