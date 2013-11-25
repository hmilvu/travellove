package com.travel.action.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.action.AuthorityAction;
import com.travel.entity.MemberInf;
import com.travel.service.MemberService;
import com.travel.utils.QRCodeUtil;


/**
 * 验证码
 * 
 * @author deniro
 */
public class MemberQrCodeAction extends AuthorityAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ByteArrayInputStream inputStream;
	
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	
	@Autowired 
	private MemberService memberService;
	
	public String execute() {
		String id = request.getParameter("memberId");
		MemberInf memberInf = memberService.getMemberById(Long.valueOf(id));
		String content = "{\"mobile\":\""+memberInf.getTravelerMobile()+"\",\"password\":\""+memberInf.getPassword()+"\"}";
		try{
			BufferedImage image = QRCodeUtil.createQRimage(content);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			ByteArrayInputStream input = new ByteArrayInputStream(
					output.toByteArray());
			this.setInputStream(input);
		}catch(Exception e){
			log.error("生成二维码错误 memberId = " + id, e);
		}
		return SUCCESS;
	}


}
