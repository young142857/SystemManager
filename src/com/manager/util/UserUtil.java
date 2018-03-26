package com.manager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.manager.pojo.User;
import com.manager.servlet.LoginServlet;

/**
 * @ClassName : UserUtil
 * @Description : 用户相关操作
 * @Author : yangyang
 * @Date : 2018年3月20日 下午3:00:30
 * @Version : V1.0
 */
public class UserUtil {
	// 数组最大容量
	private static final int MAX_SIZE = 1000;
	
	public static void main(String[] args) throws Exception {
		List<User> users = getUserInfo();
		for (User user : users) {
			System.out.println(user);
		}
	}

	/**
	 * @Title : getUserInfo
	 * @Description : 读取配置文件中的用户信息
	 * @Date : 2018年3月19日 下午2:48:07
	 * @return
	 * @throws Exception
	 */
	public static List<User> getUserInfo() throws Exception {
		List<User> userList = new ArrayList<>();
		File file = new File(LoginServlet.class.getClassLoader().getResource("userInfo.properties").getPath());
		BufferedReader reader = null;
		String temp = null;
		int flag = 0;
		String[] userArray = new String[MAX_SIZE];
		
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((temp = reader.readLine()) != null) {
				// 筛选空行
				if ("".equals(temp)) {
					continue;
				}
				// 3个一组循环存（用户名、密码、url）
				if (flag % 3 == 0) {
					userArray[flag] = temp.replaceAll("user=", "");
				} else if (flag % 3 == 1) {
					userArray[flag] = temp.replaceAll("password=", "");
				} else {
					userArray[flag] = temp.replaceAll("url=", "");
				}
				flag++;
			}
			// 遍历用户信息
			for (int i = 0; i < flag; i = i + 3) {
				User user = new User();
				user.setUser(userArray[i]);
				user.setPassword(userArray[i + 1]);
				user.setUrl(userArray[i + 2]);
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return userList;
	}

	/**
	 * @Title : judgeUser
	 * @Description : 判断用户输入信息是否匹配
	 * @Date : 2018年3月20日 下午2:59:39
	 * @param userList
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean judgeUser(List<User> userList, String userName, String password) {
		boolean result = false;
		try {
			// 判断用户输入信息是否正确
			for (User user : userList) {
				if (userName.equals(user.getUser()) && password.equals(user.getPassword())) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}
}
