package com.travel.common;

public class Constants {
	public static final String BADU_MAP_AK_KEY = "BAIDU_MAP_AK";
	public static final String BAIDU_MAP_AK = "C1fb0a7eb5bbb52a841675e1bbc5f69d";
	public static final String SYS_USER_INF_IN_SESSION = "SYS_USER_INF_IN_SESSION";
	public static final String MENU_INF_IN_SESSION = "menuInforInSession";
	public static final String ALL_MENU_INF_STR = "ALL_MENU_INF_STR";
	public static final String ALL_MENU_INF_STR_FOR_EDIT = "ALL_MENU_INF_STR_FOR_EDIT";
	public static final int DEFAULT_PAGE_SIZE = 10;	
	public static final int ADMIN_DEFAULT_PAGE_SIZE = 15;
	public static final String BAIDU_MAP_MEMBER_ID_IN_SESSION = "BAIDU_MAP_MEMBER_ID_IN_SESSION";
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
	
	public enum TEAM_STATUS {
		ACTIVE(0), INACTIVE(1);
		private int value;
		private TEAM_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MEMBER_STATUS {
		ACTIVE(0), INACTIVE(1);
		private int value;
		private MEMBER_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MEMBER_TYPE {
		GUIDE(0), DRIVER(1), TRAVELER(2);
		private int value;
		private MEMBER_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MESSAGE_STATUS {
		IN_ACTIVE(0), ACTIVE(1), DELETED(2);
		private int value;
		private MESSAGE_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MESSAGE_PRIORITY {
		TOP(0), NORMAL(1);
		private int value;
		private MESSAGE_PRIORITY(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	public enum MESSAGE_TYPE {
		NOTIFICATION(0), NOTE(1);
		private int value;
		private MESSAGE_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MESSAGE_REMIND_MODE {
		NOW(0), LATER(1);
		private int value;
		private MESSAGE_REMIND_MODE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MESSAGE_RECEIVER_TYPE {
		TEAM(0), MEMBER(1), VIEW_SPOT(2), ITEM(3);
		private int value;
		private MESSAGE_RECEIVER_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum MESSAGE_CREATE_TYPE {
		SYSUSER(0), MEMBER(1);
		private int value;
		private MESSAGE_CREATE_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum IMAGE_TYPE {
		AVATAR(0), VIEWSPOT(1), ITEM(2);
		private int value;
		private IMAGE_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum PUSH_STATUS {
		NOT_PUSH(0), PUSHED(1), PUSH_FAILED(2);
		private int value;
		private PUSH_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum ROUTE_STATUS {
		NOT_FINISH(0), FINISHED(1);
		private int value;
		private ROUTE_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum ORDER_STATUS {
		BOOKED(0), CONFIRM(1), DELETED(2);
		private int value;
		private ORDER_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum VIEW_SPOT_TYPE {
		PUBLIC(0), PRIVATE(1);
		private int value;
		private VIEW_SPOT_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	public enum ITEM_TYPE {
		PUBLIC(0), PRIVATE(1);
		private int value;
		private ITEM_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	public enum TRIGGER_STATUS {
		INACTIVE(0), ACTIVE(1);
		private int value;
		private TRIGGER_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum TRIGGER_TYPE {
		MANUAL(0), VELOCITY(1), MEMBER_DISTANCE(2), VIEW_SPOT_WARNING(3), REMIND(4), TODAY_WEATHER(5), TOMORROW_WEATHER(7),
		INSTALL(6);
		private int value;
		private TRIGGER_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum VISIBLITY{
		INVISIBLE(0), VISIBLE(1);
		private int value;
		private VISIBLITY(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	public enum VISIBLE_TYPE{
		GEO(0), PHONE(1);
		private int value;
		private VISIBLE_TYPE(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	public enum IS_NEW{
		TRUE(1), FALSE(0);
		private int value;
		private IS_NEW(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
	public enum SMS_STATUS {
		NOT_SEND(0), SENT(1), SEND_FAILED(2);
		private int value;
		private SMS_STATUS(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum SMS_TRIGGER {
		ACTIVE(1), INACTIVE(0);
		private int value;
		private SMS_TRIGGER(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
	
	public enum PUSH_TRIGGER {
		ACTIVE(1), INACTIVE(0);
		private int value;
		private PUSH_TRIGGER(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	} 
}
