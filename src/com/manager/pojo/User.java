package com.manager.pojo; 
/** 
  * @ClassName : User
  * @Description : 用户信息
  * @Author : yangyang 
  * @Date : 2018Äê3ÔÂ19ÈÕ ÏÂÎç2:42:15 
  * @Version : V1.0
  */
public class User {
	private String user;
	private String password;
	private String url;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "User [user=" + user + ", password=" + password + ", url=" + url + "]";
	}
}
