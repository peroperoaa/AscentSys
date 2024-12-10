package com.ascent.ui;

import javax.swing.*;
import javax.swing.event.*;
import com.ascent.bean.Product;
import com.ascent.util.ProductDataClient;
import com.ascent.util.ProductDataAccessor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class ProductPanel extends JPanel {

	protected JLabel selectionLabel;
	protected JComboBox categoryComboBox;
	protected JPanel topPanel;
	protected JList productListBox;
	protected JScrollPane productScrollPane;
	protected JButton detailsButton, clearButton, exitButton, shoppingButton;
	protected JPanel bottomPanel;
	protected MainFrame parentFrame;
	protected ArrayList<Product> productArrayList;
	protected ProductDataAccessor dataAccessor;

	public ProductPanel(MainFrame theParentFrame, ProductDataAccessor dataAccessor) {
		this.parentFrame = theParentFrame;
		this.dataAccessor = dataAccessor;

		selectionLabel = new JLabel("ѡ�����");
		categoryComboBox = new JComboBox();
		categoryComboBox.addItem("-------");

		// ��dataAccessor�л�ȡ�����б�
		ArrayList<String> categoryArrayList = dataAccessor.getCategories();
		for (String aCategory : categoryArrayList) {
			categoryComboBox.addItem(aCategory);
		}

		topPanel = new JPanel();
		productListBox = new JList();
		productListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productScrollPane = new JScrollPane(productListBox);

		detailsButton = new JButton("��ϸ...");
		clearButton = new JButton("���");
		exitButton = new JButton("�˳�");
		shoppingButton = new JButton("�鿴���ﳵ");

		bottomPanel = new JPanel();

		this.setLayout(new BorderLayout());
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(selectionLabel);
		topPanel.add(categoryComboBox);

		this.add(BorderLayout.NORTH, topPanel);
		this.add(BorderLayout.CENTER, productScrollPane);

		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(shoppingButton);
		bottomPanel.add(detailsButton);
		bottomPanel.add(clearButton);
		bottomPanel.add(exitButton);

		this.add(BorderLayout.SOUTH, bottomPanel);

		detailsButton.addActionListener(new DetailsActionListener());
		clearButton.addActionListener(new ClearActionListener());
		exitButton.addActionListener(new ExitActionListener());
		shoppingButton.addActionListener(new ShoppingActionListener());
		categoryComboBox.addItemListener(new GoItemListener());
		productListBox.addListSelectionListener(new ProductListSelectionListener());

		detailsButton.setEnabled(false);
		clearButton.setEnabled(false);
		shoppingButton.setEnabled(false);

	}

	/**
	 * ˢ�����ݵķ��������»�ȡ�����б�͵�ǰѡ�еķ����Ӧ�Ĳ�Ʒ�б�
	 */
	public void refreshData() {
		// ��ղ����¼��ط���
		categoryComboBox.removeAllItems();
		categoryComboBox.addItem("-------");
		for (String aCategory : dataAccessor.getCategories()) {
			categoryComboBox.addItem(aCategory);
		}

		// �����ǰѡ����ĳ�����࣬����ݸ÷������»�ȡ��Ʒ�б�
		int index = categoryComboBox.getSelectedIndex();
		if (index > 0) {
			populateListBox();
		} else {
			// �����û��ѡ����Ч���࣬����ղ�Ʒ�б�
			productListBox.setListData(new Object[0]);
		}
	}

	protected void populateListBox() {
		String category = (String) categoryComboBox.getSelectedItem();
		if (category != null && !category.startsWith("---")) {
			productArrayList = dataAccessor.getProducts(category);
		} else {
			productArrayList = new ArrayList<Product>();
		}

		Object[] theData = productArrayList.toArray();
		productListBox.setListData(theData);
		productListBox.updateUI();

		if (productArrayList.size() > 0) {
			clearButton.setEnabled(true);
		} else {
			clearButton.setEnabled(false);
		}
	}

	// ���������������֮ǰһ��

	class DetailsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int index = productListBox.getSelectedIndex();
			Product product = (Product) productArrayList.get(index);
			ProductDetailsDialog myDetailsDialog = new ProductDetailsDialog(parentFrame, product, shoppingButton);
			myDetailsDialog.setVisible(true);
		}
	}

	class ShoppingActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			ShoppingCartDialog myShoppingDialog = new ShoppingCartDialog(parentFrame, shoppingButton);
			myShoppingDialog.setVisible(true);
		}
	}

	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			parentFrame.exit();
		}
	}

	class ClearActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			productListBox.setListData(new Object[0]);
			categoryComboBox.setSelectedIndex(0);
		}
	}

	class GoItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				populateListBox();
			}
		}
	}

	class ProductListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (productListBox.isSelectionEmpty()) {
				detailsButton.setEnabled(false);
			} else {
				detailsButton.setEnabled(true);
				shoppingButton.setEnabled(true);
			}
		}
	}
}
