package com.guichaguri.minimalftp;

import com.guichaguri.minimalftp.custom.UserbaseAuthenticator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;

public class FTP_Server extends JPanel {
    private static void createAndShowGUI(FTPServer server) {
        //Create and set up the window.
        JFrame frame = new JFrame("FTP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new myGUI(server), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void startFTP(FTPServer server) throws IOException {


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

    public static void main(String[] args) throws IOException {

        // Create the FTP server
        FTPServer server = new FTPServer();
        createAndShowGUI(server);
        startFTP(server);
    }
}
// netstat -ano

