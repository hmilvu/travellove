package com.travel.common;

public class Constants {
	public static final String SYS_USER_INF_IN_SESSION = "SYS_USER_INF_IN_SESSION";
	public static final String MENU_INF_IN_SESSION = "menuInforInSession";
	public static final String ALL_MENU_INF_STR = "ALL_MENU_INF_STR";
	public static final int DEFAULT_PAGE_SIZE = 10;	
	public static final int ADMIN_DEFAULT_PAGE_SIZE = 15;
	
	//用户状态
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

}
