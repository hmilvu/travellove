/**
 * @author Zhang Zhipeng
 *
 * 2013-11-13
 */
package com.travel.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * @author Lenovo
 * 
 */
public class QRCodeUtil {

	/**
	 * 生成二维码(QRCode)图片 
	 * @param content 二维码图片的内容 
	 * @param imgPath 生成二维码图片完整的路径
	 * @param ccbpath 二维码图片中间的logo路径
	 */
	public static int createQRCode(String content, String imgPath,
			String ccbPath) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);
			// System.out.println(content);
			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage bufImg = new BufferedImage(140, 140,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140); // 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK); // 设置偏移量 不设置可能导致解析出错
			int pixoff = 2; // 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				return -1;
			}

			Image img = ImageIO.read(new File(ccbPath));// 实例化一个Image对象。
			gs.drawImage(img, 55, 55, null);
			gs.dispose();
			bufImg.flush(); // 生成二维码QRCode图片
			File imgFile = new File(imgPath);
			ImageIO.write(bufImg, "png", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
			return -100;
		}
		return 0;
	}
	
	public static void main(String []args) throws Exception{
//		createQRCode("www.163.com", "d:\\test.jpg", "D:\\projects\\github\\travellove\\WebContent\\favicon.ico");
		create_image("http://www.163.com/");
	}
	
	 
	 public static void create_image(String sms_info)throws Exception{
	  try{
	         Qrcode testQrcode =new Qrcode();
	            testQrcode.setQrcodeErrorCorrect('M');
	            testQrcode.setQrcodeEncodeMode('B');
	            testQrcode.setQrcodeVersion(7);
	            String testString = sms_info;
	            byte[] d = testString.getBytes("gbk");
	            System.out.println(d.length);
	            //BufferedImage bi = new BufferedImage(98, 98, BufferedImage.TYPE_INT_RGB);
	            BufferedImage bi = new BufferedImage(98, 98, BufferedImage.TYPE_BYTE_BINARY);
	            Graphics2D g = bi.createGraphics();
	            g.setBackground(Color.WHITE);
	            g.clearRect(0, 0, 98, 98);
	            g.setColor(Color.BLACK);
	            
	            // 限制最大字节数为120
	            if (d.length>0 && d.length <120){
	                boolean[][] s = testQrcode.calQrcode(d);
	                for (int i=0;i<s.length;i++){
	                    for (int j=0;j<s.length;j++){
	                        if (s[j][i]) {
	                            g.fillRect(j*2+3,i*2+3,2,2);
	                        }
	                    }
	                }
	            }
	            g.dispose();
	            bi.flush();
	            File f = new File("D:\\a.jpg");
	            if(!f.exists()){
	       f.createNewFile();
	            }
	            //创建图片
	            ImageIO.write(bi, "jpg", f);
	            
	        } // end try
	        catch (Exception e) {
	            e.printStackTrace();
	        } // end catch
	 }
}
