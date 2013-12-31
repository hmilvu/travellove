package com.travel.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dao.AppVersionDAO;
import com.travel.entity.AppVersion;

@Service
public class FileService extends AbstractBaseService
{
	private static final int BUFFER_SIZE = 16 * 1024;
	
	public boolean upload(File uploadFile, String dstPath, String fileName) {
		if(uploadFile == null){
			return false;
		}
		File dstDir = new File(dstPath);
		if (!dstDir.exists()) {
			dstDir.mkdir();
		}		
		File dstFile = new File(dstPath + "\\" + fileName);
		InputStream in = null;
		OutputStream out = null;
		boolean result = true;
		try {
			in = new BufferedInputStream(new FileInputStream(uploadFile), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dstFile),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error("上传图片失败", e);
			result = false;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	
}
