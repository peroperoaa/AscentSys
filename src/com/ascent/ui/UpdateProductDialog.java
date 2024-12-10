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
        super(owner, "�޸Ĳ�Ʒ", true);
        this.dataAccessor = dataAccessor;

        // ʹ��GridBagLayout�����
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5); // ���������ļ��

        // ��һ�У���Ʒ��
        contentPanel.add(new JLabel("��Ʒ��(���ɸ���):"), c);

        c.gridx = 1;
        nameField = new JTextField(15);
        contentPanel.add(nameField, c);

        // CAS��
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("CAS��:"), c);

        c.gridx = 1;
        casField = new JTextField(15);
        contentPanel.add(casField, c);

        // �ṹͼ
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("�ṹͼ:"), c);

        c.gridx = 1;
        structureField = new JTextField(15);
        contentPanel.add(structureField, c);

        // ��ʽ
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("��ʽ:"), c);

        c.gridx = 1;
        formulaField = new JTextField(15);
        contentPanel.add(formulaField, c);

        // �۸�
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("�۸�:"), c);

        c.gridx = 1;
        priceField = new JTextField(15);
        contentPanel.add(priceField, c);

        // ����(���)
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("����(���):"), c);

        c.gridx = 1;
        stockField = new JTextField(15);
        contentPanel.add(stockField, c);

        // ���
        c.gridx = 0;
        c.gridy++;
        contentPanel.add(new JLabel("���:"), c);

        c.gridx = 1;
        categoryField = new JTextField(15);
        contentPanel.add(categoryField, c);

        // ��ť���
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loadBtn = new JButton("����ԭ����");
        JButton okBtn = new JButton("�޸�");
        JButton cancelBtn = new JButton("ȡ��");
        buttonPanel.add(loadBtn);
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);

        // ������
        loadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "���������Ʒ���ƣ�");
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
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "δ�ҵ���Ʒ��");
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
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "�޸ĳɹ���");
                } else {
                    JOptionPane.showMessageDialog(UpdateProductDialog.this, "�޸�ʧ�ܣ�δ�ҵ��˲�Ʒ��");
                }
                setVisible(false);
            }
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        // ��contentPanel��buttonPanel��ӵ��Ի���
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
