package com.guichaguri.minimalftp.custom;

import com.guichaguri.minimalftp.FTPConnection;
import com.guichaguri.minimalftp.api.IFileSystem;
import com.guichaguri.minimalftp.api.IUserAuthenticator;
import com.guichaguri.minimalftp.impl.NativeFileSystem;

import java.io.File;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * A simple user base which encodes passwords in MD5 (not really for security, it's just as an example)
 *
 * @author Guilherme Chaguri
 */
public class UserbaseAuthenticator implements IUserAuthenticator {

    private Map<String, byte[]> userbase = new HashMap<>();
    private final String authFile = "./base.txt";

    public UserbaseAuthenticator() {
        this.userbase = loadMapFromFile(authFile);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, byte[]> loadMapFromFile(String path) {
        FileInputStream freader;
        try {
            Map<String, byte[]> map;
            freader = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            map = (Map<String, byte[]>) objectInputStream.readObject();
            System.out.println("Read file");
            for (String value : map.keySet()) {
                System.out.println(value);
            }
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private static void saveMapToFile(String path, Map<String, byte[]> map) {
        try {
            FileOutputStream outStream = new FileOutputStream(path, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(map);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] toMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pass.getBytes("UTF-8"));
        } catch (Exception ex) {
            return pass.getBytes();
        }
    }

    public void registerUser(String username, String password) {
        userbase.put(username, toMD5(password));
        saveMapToFile(authFile, userbase);
    }

    @Override
    public boolean needsUsername(FTPConnection con) {
        return true;
    }

    @Override
    public boolean needsPassword(FTPConnection con, String username, InetAddress address) {
        return true;
    }

    @Override
    public IFileSystem authenticate(FTPConnection con, InetAddress address, String username, String password) throws AuthException {
        // Check for a user with that username in the database
        if (!userbase.containsKey(username)) {
            throw new AuthException();
        }

        // Gets the correct, original password
        byte[] originalPass = userbase.get(username);

        // Calculates the MD5 for the given password
        byte[] inputPass = toMD5(password);

        // Check for wrong password
        if (!Arrays.equals(originalPass, inputPass)) {
            throw new AuthException();
        }

        // Use the username as a directory for file storage
        File path = new File(System.getProperty("user.dir"), "~" + username);
        // File path = new File(System.getProperty("user.dir"));
        return new NativeFileSystem(path);
    }

    public Map<String, byte[]> getUserbase() {
        return this.userbase;
    }

    public void setUserbase(Map<String, byte[]> usb) {
        this.userbase = usb;
    }
}
