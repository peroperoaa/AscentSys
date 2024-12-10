package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.ascent.bean.Product;
import com.ascent.util.ProductDataAccessor;

/**
 * 增加产品对话框
 * 让管理员输入新产品的各项信息，然后点击确定添加
 */
@SuppressWarnings("serial")
public class AddProductDialog extends JDialog {
    private JTextField nameField, casField, structureField, formulaField, priceField, stockField, categoryField;
    private ProductDataAccessor dataAccessor;

    public AddProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "增加产品", true);
        this.dataAccessor = dataAccessor;

        setLayout(new GridLayout(8, 2));
        add(new JLabel("产品名:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("CAS号:"));
        casField = new JTextField();
        add(casField);

        add(new JLabel("结构图:"));
        structureField = new JTextField();
        add(structureField);

        add(new JLabel("公式:"));
        formulaField = new JTextField();
        add(formulaField);

        add(new JLabel("价格:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("数量(库存):"));
        stockField = new JTextField();
        add(stockField);

        add(new JLabel("类别:"));
        categoryField = new JTextField();
        add(categoryField);

        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");
        add(okBtn);
        add(cancelBtn);

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 校验非空
                if (isAnyFieldEmpty()) {
                    JOptionPane.showMessageDialog(AddProductDialog.this, "有信息为空，请完整填写药品信息！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = nameField.getText().trim();
                String cas = casField.getText().trim();
                String structure = structureField.getText().trim();
                String formula = formulaField.getText().trim();
                String price = priceField.getText().trim();
                String stock = stockField.getText().trim();
                String category = categoryField.getText().trim();

                Product p = new Product(name, cas, structure, formula, price, stock, category);
                dataAccessor.addProduct(p);
                dataAccessor.saveAllProducts(); // 保存到文件
                JOptionPane.showMessageDialog(AddProductDialog.this, "添加成功！");
                setVisible(false);
            }
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(owner);
    }

    private boolean isAnyFieldEmpty() {
        return nameField.getText().trim().isEmpty() ||
                casField.getText().trim().isEmpty() ||
                structureField.getText().trim().isEmpty() ||
                formulaField.getText().trim().isEmpty() ||
                priceField.getText().trim().isEmpty() ||
                stockField.getText().trim().isEmpty() ||
                categoryField.getText().trim().isEmpty();
    }
}
