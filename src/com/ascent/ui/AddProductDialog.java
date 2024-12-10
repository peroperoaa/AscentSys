package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.ascent.bean.Product;
import com.ascent.util.ProductDataAccessor;

/**
 * ���Ӳ�Ʒ�Ի���
 * �ù���Ա�����²�Ʒ�ĸ�����Ϣ��Ȼ����ȷ�����
 */
@SuppressWarnings("serial")
public class AddProductDialog extends JDialog {
    private JTextField nameField, casField, structureField, formulaField, priceField, stockField, categoryField;
    private ProductDataAccessor dataAccessor;

    public AddProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "���Ӳ�Ʒ", true);
        this.dataAccessor = dataAccessor;

        setLayout(new GridLayout(8, 2));
        add(new JLabel("��Ʒ��:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("CAS��:"));
        casField = new JTextField();
        add(casField);

        add(new JLabel("�ṹͼ:"));
        structureField = new JTextField();
        add(structureField);

        add(new JLabel("��ʽ:"));
        formulaField = new JTextField();
        add(formulaField);

        add(new JLabel("�۸�:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("����(���):"));
        stockField = new JTextField();
        add(stockField);

        add(new JLabel("���:"));
        categoryField = new JTextField();
        add(categoryField);

        JButton okBtn = new JButton("ȷ��");
        JButton cancelBtn = new JButton("ȡ��");
        add(okBtn);
        add(cancelBtn);

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // У��ǿ�
                if (isAnyFieldEmpty()) {
                    JOptionPane.showMessageDialog(AddProductDialog.this, "����ϢΪ�գ���������дҩƷ��Ϣ��", "����", JOptionPane.ERROR_MESSAGE);
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
                dataAccessor.saveAllProducts(); // ���浽�ļ�
                JOptionPane.showMessageDialog(AddProductDialog.this, "��ӳɹ���");
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
