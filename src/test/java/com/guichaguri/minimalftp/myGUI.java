package com.guichaguri.minimalftp;

import com.guichaguri.minimalftp.custom.UserbaseAuthenticator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;

public class myGUI extends JPanel {
    public myGUI() {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        // ImageIcon icon = createImageIcon("1.jpg");
        ImageIcon icon1 = null;

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

        JPanel panel2 = new JPanel();
        JFileChooser fc = new JFileChooser("G:\\projects\\softB\\MinimalFTP\\~john");
        panel2.add(fc);
        tabbedPane.addTab("Explorer", icon1, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JPanel panel3 = new JPanel();
        JButton start_ftp = new JButton();
        start_ftp.setText("start");
        start_ftp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startFTP();
                    // TODO
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        panel3.add(start_ftp);
        tabbedPane.addTab("Explorer", icon1, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        // Add the tabbed pane to this panel.
        add(tabbedPane);

        // The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private static void startFTP() throws IOException {

        // Create the FTP server
        FTPServer server = new FTPServer();

        // Create our custom authenticator
        UserbaseAuthenticator auth = new UserbaseAuthenticator();

        // Register a few users
        auth.registerUser("john", "1234");

        // Set our custom authenticator
        server.setAuthenticator(auth);

        // Register an instance of this class as a listener
        server.addListener(new CustomServer());

        // Changes the timeout to 10 minutes
        server.setTimeout(10 * 60 * 1000); // 10 minutes

        // Changes the buffer size
        server.setBufferSize(1024 * 5); // 5 kilobytes

        // change host here
        server.listenSync(InetAddress.getByName("10.250.154.69"), 21);
    }

}
