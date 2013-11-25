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
	public static BufferedImage createQRimage(String content) throws Exception {
		try {
			Qrcode testQrcode = new Qrcode();
			testQrcode.setQrcodeErrorCorrect('M');
			testQrcode.setQrcodeEncodeMode('B');
			testQrcode.setQrcodeVersion(7);
			byte[] d = content.getBytes("gbk");
			System.out.println(d.length);
			BufferedImage bi = new BufferedImage(98, 98,
					BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D g = bi.createGraphics();
			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, 98, 98);
			g.setColor(Color.BLACK);

			// 限制最大字节数为120
			if (d.length > 0 && d.length < 120) {
				boolean[][] s = testQrcode.calQrcode(d);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							g.fillRect(j * 2 + 3, i * 2 + 3, 2, 2);
						}
					}
				}
			}
			g.dispose();
			bi.flush();
			return bi;
		} // end try
		catch (Exception e) {
			e.printStackTrace();
		} // end catch
		return null;
	}
}
