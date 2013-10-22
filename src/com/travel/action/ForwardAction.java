package com.travel.action;


/**
 * 跳转
 * 
 * @author deniro
 * 
 */
public class ForwardAction extends AuthorityAction {

	private static final long serialVersionUID = 1L;

	public String tSshList() {
		return "tSsh-list";
	}

	public String download() {
		return "download";
	}

	/**
	 * 跳转 地图页
	 * 
	 * @return
	 */
	public String map() {
		return "map";
	}

	/**
	 * 跳转 （DWZ-UI）首页
	 * 
	 * @return
	 */
	public String dwz() {
		return "dwz";
	}

	/**
	 * 跳转 上传页
	 * 
	 * @return
	 */
	public String upload() {
		return "upload";
	}
}
