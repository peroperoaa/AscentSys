package com.ascent.ui;

import com.ascent.util.ProductDataAccessor;

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

    //v1.0.1
    private Frame parentFrame;
    private ProductDataAccessor dataAccessor;

    public ManagePanel(Frame parentFrame, ProductDataAccessor dataAccessor) {
        this.parentFrame = parentFrame;
        this.dataAccessor = dataAccessor;

        this.setLayout(new FlowLayout());

        addButton = new JButton("增加药品");
        deleteButton = new JButton("删除药品");
        updateButton = new JButton("修改药品");
        queryButton = new JButton("查询药品");

        this.add(addButton);
        this.add(deleteButton);
        this.add(updateButton);
        this.add(queryButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddProductDialog dlg = new AddProductDialog(parentFrame, dataAccessor);
                dlg.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteProductDialog dlg = new DeleteProductDialog(parentFrame, dataAccessor);
                dlg.setVisible(true);
            }
        });


        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateProductDialog dlg = new UpdateProductDialog(parentFrame, dataAccessor);
                dlg.setVisible(true);
            }
        });

        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                QueryProductDialog dlg = new QueryProductDialog(parentFrame, dataAccessor);
                dlg.setVisible(true);
            }
        });
    }
}
