/**
 * @author Zhang Zhipeng
 *
 * 2013-10-22
 */
package com.travel.action.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.BaseAction;
import com.travel.common.dto.FailureResult;
import com.travel.common.dto.MessageDTO;
import com.travel.common.dto.PageInfoDTO;
import com.travel.common.dto.ReplyDTO;
import com.travel.common.dto.SuccessResult;
import com.travel.entity.Message;
import com.travel.service.MessageService;

/**
 * @author Lenovo
 * 
 */
public class MessageAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MessageService messageService;
	
		
	public void list(){
		String data = getMobileData();
		Object memberId = getMobileParameter(data, "memberId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(memberId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		Object pageSize = binder.getValue(data, "pageSize");
		Object pageNumber = binder.getValue(data, "pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		List<MessageDTO> list = messageService.getMessageByMemberId(idLong, pageInfo);
		SuccessResult<List<MessageDTO>> result = new SuccessResult<List<MessageDTO>>(list);
		sendToMobile(result);
		
	}
	
	public void detail(){
		String data = getMobileData();
		Object msgId = getMobileParameter(data, "id");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(msgId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		Message msg = messageService.getMessageById(idLong);
		SuccessResult<MessageDTO> result = new SuccessResult<MessageDTO>(msg.toDTO());
		sendToMobile(result);
	}
	
	public void replies(){
		String data = getMobileData();
		Object messageId = getMobileParameter(data, "messageId");
		Long idLong = Long.valueOf(0);
		try {
			idLong = Long.valueOf(messageId.toString());
		} catch (Throwable e) {
			FailureResult result = new FailureResult("id类型错误");
			sendToMobile(result);
			return;
		}
		Object pageSize = binder.getValue(data, "pageSize");
		Object pageNumber = binder.getValue(data, "pageNumber");
		PageInfoDTO pageInfo = new PageInfoDTO();
		try{
			pageInfo.setPageNumber(Integer.valueOf(pageNumber.toString()));
			pageInfo.setPageSize(Integer.valueOf(pageSize.toString()));
		}catch(Throwable ignore){			
		}
		List<ReplyDTO> list = messageService.getRepliesByMessageId(idLong, pageInfo);
		SuccessResult<List<ReplyDTO>> result = new SuccessResult<List<ReplyDTO>>(list);
		sendToMobile(result);
	}
}
