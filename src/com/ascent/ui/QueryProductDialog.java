package com.ascent.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.ascent.bean.Product;
import com.ascent.util.ProductDataAccessor;

/**
 * ��ѯ��Ʒ�Ի���
 * �����Ʒ���ƣ������ϸ��Ϣ
 */
@SuppressWarnings("serial")
public class QueryProductDialog extends JDialog {
    private JTextField nameField;
    private JTextArea resultArea;
    private ProductDataAccessor dataAccessor;

    public QueryProductDialog(Frame owner, ProductDataAccessor dataAccessor) {
        super(owner, "��ѯ��Ʒ", true);
        this.dataAccessor = dataAccessor;

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("�����Ʒ����:"));
        nameField = new JTextField(20);
        topPanel.add(nameField);

        JButton queryBtn = new JButton("��ѯ");
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
                    resultArea.setText("��Ʒ��: " + p.getProductname() + "\n" +
                            "CAS��: " + p.getCas() + "\n" +
                            "�ṹͼ: " + p.getStructure() + "\n" +
                            "��ʽ: " + p.getFormula() + "\n" +
                            "�۸�: " + p.getPrice() + "\n" +
                            "����: " + p.getRealstock() + "\n" +
                            "���: " + p.getCategory());
                } else {
                    resultArea.setText("δ�ҵ��ò�Ʒ��");
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
