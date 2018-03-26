package com.manager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manager.pojo.User;
import com.manager.util.AesUtil;
import com.manager.util.UserUtil;

/**
 * @ClassName : LoginServlet
 * @Description : 用户登录
 * @Author : yangyang
 * @Date : 2018年3月19日 上午11:45:00
 * @Version : V1.0
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	// 登录成功跳转页面
	private static final String SUCCESS_VIEW = "login.html";
	// 登录失败跳转页面
	private static final String FAILED_VIEW = "failed.html";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		List<User> userList = new ArrayList<>();

		try {
			String userName = req.getParameter("user");
			String passwordInput = req.getParameter("password");
			// 明文加密
			String password = AesUtil.encrypt(passwordInput);
			System.out.println("明文:" + passwordInput + "  " + "密文:" + password);
			// 获取用户信息集合
			userList = UserUtil.getUserInfo();
			// 判断用户输入是否匹配
			boolean result = UserUtil.judgeUser(userList, userName, password);
			if (result) { // 登录成功
				resp.sendRedirect(SUCCESS_VIEW);
				// req.getRequestDispatcher(SUCCESS_VIEW).forward(req, resp);
			} else { // 登录失败
				resp.sendRedirect(FAILED_VIEW);
				// req.getRequestDispatcher(FAILED_VIEW).forward(req, resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}