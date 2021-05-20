package com.guichaguri.minimalftp;

import com.guichaguri.minimalftp.custom.UserbaseAuthenticator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class myGUI extends JPanel {
    public myGUI(FTPServer server) throws UnknownHostException {
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
                String username = jt1.getText();
                String password = jt2.getText();
                System.out.println(username + "\t" + password);
                server.getAuthenticator().registerUser(username, password);
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
        JFileChooser fc = new JFileChooser("./");
        panel2.add(fc);
        tabbedPane.addTab("Explorer", icon1, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // p3:methods to ftp. contain: close and update

        try {
            Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();
            while (faces.hasMoreElements()) { // 遍历网络接口
                NetworkInterface face = faces.nextElement();
                if (face.isLoopback() || face.isVirtual() || !face.isUp()) {
                    continue;
                }
                System.out.print("\nName:" + face.getDisplayName() + ",Address:");
                Enumeration<InetAddress> address = face.getInetAddresses();
                while (address.hasMoreElements()) { // 遍历网络地址
                    InetAddress addr = address.nextElement();
                    if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress() && !addr.isAnyLocalAddress()) {
                        System.out.print(addr.getHostAddress() + " ");
                    }
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        JPanel panel3 = new JPanel();
        InetAddress host = InetAddress.getLocalHost();
        System.out.println(host);
        JLabel jl = new JLabel("host.toString()");

        panel3.add(jl);
        tabbedPane.addTab("about", icon1, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        // Add the tabbed pane to this panel.
        add(tabbedPane);

        // The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
