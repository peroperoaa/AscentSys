package com.ascent.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import com.ascent.bean.User;
import com.ascent.util.UserDataClient;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {

	protected JTextField userText;
	protected JPasswordField password;
	protected JLabel tip;
	protected UserDataClient userDataClient;

	public LoginFrame() {
		setTitle("用户登陆");
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel loginPanel = new JPanel();

		JLabel userLabel = new JLabel("用户帐号：");
		JLabel passwordLabel = new JLabel("用户密码：");

		userText = new JTextField(22);
		password = new JPasswordField(22);

		JButton loginButton = new JButton("登陆");
		JButton regist = new JButton("注册");
		JButton tempLoginButton = new JButton("临时登录");
		JButton exitButton = new JButton("退出");

		loginPanel.add(userLabel);
		loginPanel.add(new JScrollPane(userText));
		loginPanel.add(passwordLabel);
		loginPanel.add(new JScrollPane(password));
		loginPanel.add(loginButton);
		loginPanel.add(regist);
		loginPanel.add(tempLoginButton);
		loginPanel.add(exitButton);

		setResizable(false);
		setSize(350, 150);
		setLocation(300, 100);

		JPanel tipPanel = new JPanel();
		tip = new JLabel();
		tipPanel.add(tip);

		container.add(BorderLayout.CENTER, loginPanel);
		container.add(BorderLayout.NORTH, tip);

		exitButton.addActionListener(new ExitActionListener());
		loginButton.addActionListener(new LoginActionListener());
		regist.addActionListener(new RegistActionListener());
		tempLoginButton.addActionListener(new TempLoginActionListener());
		this.addWindowListener(new WindowCloser());

		try {
			userDataClient = new UserDataClient();
		} catch (IOException e) {
			e.printStackTrace();
			tip.setText("服务器连接失败.");
		}

		setLocationRelativeTo(null);
	}

	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
			dispose();
			userDataClient.closeSocKet();
		}
	}

	class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean bo = false;
			HashMap userTable = userDataClient.getUsers();
			User currentUser = null;
			if (userTable != null) {
				if (userTable.containsKey(userText.getText())) {
					currentUser = (User) userTable.get(userText.getText());
					char[] chr = password.getPassword();
					String pwd = new String(chr);
					if (currentUser.getPassword().equals(pwd)) {
						bo = true;
					}
				}
				if (bo) {
					userDataClient.closeSocKet();
					setVisible(false);
					dispose();
					MainFrame myFrame = new MainFrame(currentUser); // 传入当前用户对象
					myFrame.setVisible(true);
				} else {
					tip.setText("帐号不存在,或密码错误.");
				}
			} else {
				tip.setText("服务器连接失败,请稍候再试.");
			}
		}
	}

	class RegistActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			RegistFrame registFrame = new RegistFrame();
			registFrame.setVisible(true);
		}
	}

	class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			setVisible(false);
			dispose();
			userDataClient.closeSocKet();
		}
	}

	class TempLoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			userDataClient.closeSocKet();
			setVisible(false);
			dispose();
			// 临时登录用户，authority=0的User对象
			User tempUser = new User("tempUser", "noPass", 0);
			MainFrame myFrame = new MainFrame(tempUser);
			myFrame.setVisible(true);
		}
	}
}
