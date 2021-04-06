package com.guichaguri.minimalftp;

import sun.jvm.hotspot.types.JBooleanField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class myGUI extends JPanel {
    public myGUI(FTPServer server) {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        // ImageIcon icon = createImageIcon("1.jpg");
        ImageIcon icon1 = null;

        // p1:register
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEADING, 150, 20));
        JTextField jt1 = new JTextField();
        jt1.addFocusListener(new JTextFieldHintListener(jt1, "enter your account"));
        jt1.setColumns(20);
        JTextField jt2 = new JTextField();
        jt2.addFocusListener(new JTextFieldHintListener(jt2, "enter your password"));
        jt2.setColumns(20);
        JButton jb = new JButton();
        jb.setText("register");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jb, "ok");
            }
        });
        panel1.add(jt1);
        panel1.add(jt2);
        panel1.add(jb);
        panel1.setPreferredSize(new Dimension(300, 100));
        tabbedPane.addTab("register tab", icon1, panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        // p2:view files and folders
        JPanel panel2 = new JPanel();
        JFileChooser fc = new JFileChooser("G:\\projects\\softB\\MinimalFTP\\~john");
        panel2.add(fc);
        tabbedPane.addTab("Explorer", icon1, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // p3:methods to ftp. contain: close\update\
        JPanel panel3 = new JPanel();

        JButton close_ftp = new JButton();
        close_ftp.setText("close");
        close_ftp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JButton update_ftp = new JButton();
        update_ftp.setText("update");
        update_ftp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO

            }
        });

        panel3.add(close_ftp);

        tabbedPane.addTab("Explorer", icon1, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        // Add the tabbed pane to this panel.
        add(tabbedPane);

        // The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
