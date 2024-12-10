package com.ascent.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 管理面板，只有管理员用户（authority=1）可访问
 */
@SuppressWarnings("serial")
public class ManagePanel extends JPanel {

    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton queryButton;

    public ManagePanel() {
        this.setLayout(new FlowLayout());

        addButton = new JButton("增加药品");
        deleteButton = new JButton("删除药品");
        updateButton = new JButton("修改药品");
        queryButton = new JButton("查询药品");

        this.add(addButton);
        this.add(deleteButton);
        this.add(updateButton);
        this.add(queryButton);

        // 以下为示例事件监听器，请根据业务逻辑进行修改
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: 打开新增药品对话框
                JOptionPane.showMessageDialog(ManagePanel.this, "执行增加药品操作");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: 打开删除药品对话框或逻辑
                JOptionPane.showMessageDialog(ManagePanel.this, "执行删除药品操作");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: 打开修改药品对话框或逻辑
                JOptionPane.showMessageDialog(ManagePanel.this, "执行修改药品操作");
            }
        });

        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: 打开查询药品对话框或逻辑
                JOptionPane.showMessageDialog(ManagePanel.this, "执行查询药品操作");
            }
        });
    }
}
