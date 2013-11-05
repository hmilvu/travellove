package com.travel.common;

public class Constants {
	public static final String SYS_USER_INF_IN_SESSION = "SYS_USER_INF_IN_SESSION";
	public static final String MENU_INF_IN_SESSION = "menuInforInSession";
	public static final String ALL_MENU_INF_STR = "ALL_MENU_INF_STR";
	public static final String ALL_MENU_INF_STR_FOR_EDIT = "ALL_MENU_INF_STR_FOR_EDIT";
	public static final int DEFAULT_PAGE_SIZE = 10;	
	public static final int ADMIN_DEFAULT_PAGE_SIZE = 15;
	
	//客户端类型
	public enum OS_TYPE {
		ANDROID(0), IOS(1), WP(2);
		private int value;
		private OS_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	public enum SYS_USER_TYPE {
		SUPER_ADMIN(0), SYSTEM_USER(1), TRAVEL_USER(2);
		private int value;
		private SYS_USER_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum SYS_USER_STATUS {
		IN_ACTIVE(0), ACTIVE(1), INVALID(2);
		private int value;
		private SYS_USER_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 

}
