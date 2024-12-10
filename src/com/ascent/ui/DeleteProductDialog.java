package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.ascent.util.ProductDataAccessor;

/**
 * ɾ����Ʒ�Ի���
 * ����Ҫɾ���Ĳ�Ʒ���ƣ����ȷ��ɾ��
 */
@SuppressWarnings("serial")
public class DeleteProductDialog extends JDialog {
    private JTextField nameField;
    private ProductDataAccessor dataAccessor;

    public DeleteProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "ɾ����Ʒ", true);
        this.dataAccessor = dataAccessor;

        setLayout(new FlowLayout());
        add(new JLabel("����Ҫɾ���Ĳ�Ʒ����:"));
        nameField = new JTextField(20);
        add(nameField);

        JButton okBtn = new JButton("ȷ��");
        JButton cancelBtn = new JButton("ȡ��");
        add(okBtn);
        add(cancelBtn);

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                boolean result = dataAccessor.deleteProduct(name);
                if (result) {
                    JOptionPane.showMessageDialog(DeleteProductDialog.this, "ɾ���ɹ���");
                } else {
                    JOptionPane.showMessageDialog(DeleteProductDialog.this, "δ�ҵ���Ʒ��");
                }
                setVisible(false);
            }
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        pack();
        setLocationRelativeTo(owner);
    }
}
