package com.travel.common;

public class Constants {
	public static final int DEFAULT_PAGE_SIZE = 10;	
	
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
