package com.ascent.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������壬ֻ�й���Ա�û���authority=1���ɷ���
 */
@SuppressWarnings("serial")
public class ManagePanel extends JPanel {

    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton queryButton;

    public ManagePanel() {
        this.setLayout(new FlowLayout());

        addButton = new JButton("����ҩƷ");
        deleteButton = new JButton("ɾ��ҩƷ");
        updateButton = new JButton("�޸�ҩƷ");
        queryButton = new JButton("��ѯҩƷ");

        this.add(addButton);
        this.add(deleteButton);
        this.add(updateButton);
        this.add(queryButton);

        // ����Ϊʾ���¼��������������ҵ���߼������޸�
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: ������ҩƷ�Ի���
                JOptionPane.showMessageDialog(ManagePanel.this, "ִ������ҩƷ����");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: ��ɾ��ҩƷ�Ի�����߼�
                JOptionPane.showMessageDialog(ManagePanel.this, "ִ��ɾ��ҩƷ����");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: ���޸�ҩƷ�Ի�����߼�
                JOptionPane.showMessageDialog(ManagePanel.this, "ִ���޸�ҩƷ����");
            }
        });

        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: �򿪲�ѯҩƷ�Ի�����߼�
                JOptionPane.showMessageDialog(ManagePanel.this, "ִ�в�ѯҩƷ����");
            }
        });
    }
}
