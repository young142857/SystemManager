package com.manager.pojo; 
/** 
  * @ClassName : RedisData
  * @Description : 
  * @Author : yangyang 
  * @Date : 2018年3月23日 10:01:30 
  * @Version : V1.0
  */
public class RedisData {
	private String key;
	private String value;
	
	public RedisData(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "RedisData [key=" + key + ", value=" + value + "]";
	}
}
