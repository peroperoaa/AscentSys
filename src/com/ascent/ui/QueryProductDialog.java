package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.ascent.bean.Product;
import com.ascent.util.ProductDataAccessor;

/**
 * 查询产品对话框
 * 输入产品名称，查出详细信息
 */
@SuppressWarnings("serial")
public class QueryProductDialog extends JDialog {
    private JTextField nameField;
    private JTextArea resultArea;
    private ProductDataAccessor dataAccessor;

    public QueryProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "查询产品", true);
        this.dataAccessor = dataAccessor;

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("输入产品名称:"));
        nameField = new JTextField(20);
        topPanel.add(nameField);

        JButton queryBtn = new JButton("查询");
        topPanel.add(queryBtn);

        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                Product p = findProductByName(name);
                if (p != null) {
                    resultArea.setText("产品名: " + p.getProductname() + "\n" +
                            "CAS号: " + p.getCas() + "\n" +
                            "结构图: " + p.getStructure() + "\n" +
                            "公式: " + p.getFormula() + "\n" +
                            "价格: " + p.getPrice() + "\n" +
                            "数量: " + p.getRealstock() + "\n" +
                            "类别: " + p.getCategory());
                } else {
                    resultArea.setText("未找到该产品！");
                }
            }
        });

        pack();
        setLocationRelativeTo(owner);
    }

    private Product findProductByName(String name) {
        for (String category : dataAccessor.getCategories()) {
            for (Product p : dataAccessor.getProducts(category)) {
                if (p.getProductname().equalsIgnoreCase(name)) {
                    return p;
                }
            }
        }
        return null;
    }
}
