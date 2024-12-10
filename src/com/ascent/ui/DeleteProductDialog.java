package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.ascent.util.ProductDataAccessor;

/**
 * 删除产品对话框
 * 输入要删除的产品名称，点击确定删除
 */
@SuppressWarnings("serial")
public class DeleteProductDialog extends JDialog {
    private JTextField nameField;
    private ProductDataAccessor dataAccessor;

    public DeleteProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "删除产品", true);
        this.dataAccessor = dataAccessor;

        setLayout(new FlowLayout());
        add(new JLabel("输入要删除的产品名称:"));
        nameField = new JTextField(20);
        add(nameField);

        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");
        add(okBtn);
        add(cancelBtn);

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                boolean result = dataAccessor.deleteProduct(name);
                if (result) {
                    JOptionPane.showMessageDialog(DeleteProductDialog.this, "删除成功！");
                } else {
                    JOptionPane.showMessageDialog(DeleteProductDialog.this, "未找到产品！");
                }
                setVisible(false);
            }
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(owner);
    }
}
