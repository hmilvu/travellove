package com.travel.action.admin;

import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

import com.travel.action.AuthorityAction;

public class DownloadTemplateAction extends AuthorityAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;// 初始的通过param指定的文件名属性   
	  
    private String inputPath;// 指定要被下载的文件路径     
  
    public InputStream getInputStream() throws Exception {   
        // 通过 ServletContext，也就是application 来读取数据   
        return ServletActionContext.getServletContext().getResourceAsStream(inputPath);   
   }   
  
    public String execute() throws Exception {     	  
        // 文件下载目录路径   
        String downloadDir = ServletActionContext.getServletContext().getRealPath("/download");        
        // 文件下载路径        
        String downloadFile = ServletActionContext.getServletContext().getRealPath(inputPath);        
        java.io.File file = new java.io.File(downloadFile);        
        downloadFile = file.getCanonicalPath();// 真实文件路径,去掉里面的..等信息       
        // 发现企图下载不在 /download 下的文件, 就显示空内容   
        if(!downloadFile.startsWith(downloadDir)) {     
            return null;     
        }      
        return SUCCESS;      
    }    
    
    public void setInputPath(String value) {   
        inputPath = value;   
    }   
  
    public void setFileName(String fileName) {     
        this.fileName = fileName;    
    }   
  
    /** 提供转换编码后的供下载用的文件名 */   
    public String getDownloadFileName() {   
        String downFileName = fileName;   
        try {   
            downFileName = new String(downFileName.getBytes(), "ISO8859-1");   
        } catch (Throwable e) {   
            log.error("下载文件失败 filename = " + downFileName, e);
        }   
        return downFileName;   
  
    }   
}
