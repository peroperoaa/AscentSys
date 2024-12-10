package com.ascent.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

import com.ascent.bean.User;
import com.ascent.util.ProductDataAccessor;

/**
 * 艾斯医药主框架界面
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	protected JTabbedPane tabbedPane;
	protected ProductPanel productPanel;
	protected ManagePanel managePanel; // 新增的管理面板

	protected User currentUser; // 存储当前登录用户信息
	protected ProductDataAccessor productDataAccessor;

	public MainFrame(User user) {
		this.currentUser = user; // 接收当前用户对象

		setTitle("欢迎使用AscentSys应用! ");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();

		//v.1.0.1
		productDataAccessor = new ProductDataAccessor();
		productDataAccessor.load(); // 初次加载数据 v1.0.2
		productPanel = new ProductPanel(this, productDataAccessor);//v1.0.2
		tabbedPane.addTab("药品", productPanel);

		if (currentUser != null && currentUser.getAuthority() == 1) {
			// 将productDataAccessor传给ManagePanel
			managePanel = new ManagePanel(this, productDataAccessor);
			tabbedPane.addTab("管理", managePanel);
		}

		container.add(BorderLayout.CENTER, tabbedPane);

		// 添加选项卡监听器 v1.0.2
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
				String title = tabbedPane.getTitleAt(index);
				if ("药品".equals(title)) {
					// 当点击“药品”标签时重新加载数据并刷新界面
					productDataAccessor.load();
					productPanel.refreshData();
				}
			}
		});

		JMenuBar myMenuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("文件");

		JMenu openMenu = new JMenu("打开");
		JMenuItem localMenuItem = new JMenuItem("本地硬盘...");
		openMenu.add(localMenuItem);

		JMenuItem networkMenuItem = new JMenuItem("网络...");
		openMenu.add(networkMenuItem);

		JMenuItem webMenuItem = new JMenuItem("互联网...");
		openMenu.add(webMenuItem);
		fileMenu.add(openMenu);

		JMenuItem saveMenuItem = new JMenuItem("保存");
		fileMenu.add(saveMenuItem);

		JMenuItem exitMenuItem = new JMenuItem("退出");
		fileMenu.add(exitMenuItem);

		myMenuBar.add(fileMenu);

		exitMenuItem.addActionListener(new ExitActionListener());

		setupLookAndFeelMenu(myMenuBar);

		JMenu helpMenu = new JMenu("帮助");
		JMenuItem aboutMenuItem = new JMenuItem("关于");
		helpMenu.add(aboutMenuItem);

		myMenuBar.add(helpMenu);

		aboutMenuItem.addActionListener(new AboutActionListener());

		this.setJMenuBar(myMenuBar);

		setSize(500, 400);
		setLocation(100, 100);

		this.addWindowListener(new WindowCloser());

		fileMenu.setMnemonic('f');
		exitMenuItem.setMnemonic('x');
		helpMenu.setMnemonic('h');
		aboutMenuItem.setMnemonic('a');

		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));

		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));

		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));

	}

	protected void setupLookAndFeelMenu(JMenuBar theMenuBar) {
		UIManager.LookAndFeelInfo[] lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
		JMenu lookAndFeelMenu = new JMenu("选项");
		LookAndFeelListener myListener = new LookAndFeelListener();

		try {
			for (int i = 0; i < lookAndFeelInfo.length; i++) {
				JMenuItem anItem = new JMenuItem(lookAndFeelInfo[i].getName() + " 外观");
				anItem.setActionCommand(lookAndFeelInfo[i].getClassName());
				anItem.addActionListener(myListener);
				lookAndFeelMenu.add(anItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		theMenuBar.add(lookAndFeelMenu);
	}

	public void exit() {
		setVisible(false);
		dispose();
		System.exit(0);
	}

	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			exit();
		}
	}

	class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			exit();
		}
	}

	class LookAndFeelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String className = event.getActionCommand();
			try {
				UIManager.setLookAndFeel(className);
				SwingUtilities.updateComponentTreeUI(MainFrame.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class AboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String msg = "超值享受!";
			JOptionPane.showMessageDialog(MainFrame.this, msg);
		}
	}
}
