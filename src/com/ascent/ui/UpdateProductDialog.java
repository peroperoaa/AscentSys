package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.ascent.bean.Product;
import com.ascent.util.ProductDataAccessor;

@SuppressWarnings("serial")
public class UpdateProductDialog extends JDialog {
    private JTextField nameField, casField, structureField, formulaField, priceField, stockField, categoryField;
    private ProductDataAccessor dataAccessor;

    public UpdateProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "修改产品", true);
        this.dataAccessor = dataAccessor;

        // 使用GridBagLayout更灵活
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5); // 设置组件间的间距

        // 第一行：产品名
        contentPanel.add(new JLabel("产品名(不可更改):"), c);

        c.gridx = 1;
        nameField = new JTextField(15);
        contentPanel.add(nameField, c);

        // CAS号
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("CAS号:"), c);

        c.gridx = 1;
        casField = new JTextField(15);
        contentPanel.add(casField, c);

        // 结构图
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("结构图:"), c);

        c.gridx = 1;
        structureField = new JTextField(15);
        contentPanel.add(structureField, c);

        // 公式
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("公式:"), c);

        c.gridx = 1;
        formulaField = new JTextField(15);
        contentPanel.add(formulaField, c);

        // 价格
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("价格:"), c);

        c.gridx = 1;
        priceField = new JTextField(15);
        contentPanel.add(priceField, c);

        // 数量(库存)
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("数量(库存):"), c);

        c.gridx = 1;
        stockField = new JTextField(15);
        contentPanel.add(stockField, c);

        // 类别
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("类别:"), c);

        c.gridx = 1;
        categoryField = new JTextField(15);
        contentPanel.add(categoryField, c);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loadBtn = new JButton("加载原数据");
        JButton okBtn = new JButton("修改");
        JButton cancelBtn = new JButton("取消");
        buttonPanel.add(loadBtn);
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);

        // 监听器
        loadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "请先输入产品名称！");
                    return;
                }
                Product original = findProductByName(name);
                if (original != null) {
                    casField.setText(original.getCas());
                    structureField.setText(original.getStructure());
                    formulaField.setText(original.getFormula());
                    priceField.setText(original.getPrice());
                    stockField.setText(original.getRealstock());
                    categoryField.setText(original.getCategory());
                } else {
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "未找到产品！");
                }
            }
        });

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                Product updated = new Product(
                        name,
                        casField.getText().trim(),
                        structureField.getText().trim(),
                        formulaField.getText().trim(),
                        priceField.getText().trim(),
                        stockField.getText().trim(),
                        categoryField.getText().trim()
                );
                boolean result = dataAccessor.updateProduct(updated);
                if (result) {
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "修改成功！");
                } else {
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "修改失败，未找到此产品！");
                }
                setVisible(false);
            }
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        // 将contentPanel与buttonPanel添加到对话框
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

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
