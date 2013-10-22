package com.travel.utils;
/**
 *
 *
 * @author deniro
 */
public class Singleton {
	//使用静态变量缓存实例
	private static Singleton instance=null;
	
	//使用静态方法，返回实例
	public static synchronized Singleton getInstance(){
		if(instance==null){//确保只产生一个对象
			instance=new Singleton();
		}
		return instance;
	}
}
