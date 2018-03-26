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
 * @Description : �û���¼
 * @Author : yangyang
 * @Date : 2018��3��19�� ����11:45:00
 * @Version : V1.0
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	// ��¼�ɹ���תҳ��
	private static final String SUCCESS_VIEW = "login.html";
	// ��¼ʧ����תҳ��
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
			// ���ļ���
			String password = AesUtil.encrypt(passwordInput);
			System.out.println("����:" + passwordInput + "  " + "����:" + password);
			// ��ȡ�û���Ϣ����
			userList = UserUtil.getUserInfo();
			// �ж��û������Ƿ�ƥ��
			boolean result = UserUtil.judgeUser(userList, userName, password);
			if (result) { // ��¼�ɹ�
				resp.sendRedirect(SUCCESS_VIEW);
				// req.getRequestDispatcher(SUCCESS_VIEW).forward(req, resp);
			} else { // ��¼ʧ��
				resp.sendRedirect(FAILED_VIEW);
				// req.getRequestDispatcher(FAILED_VIEW).forward(req, resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}