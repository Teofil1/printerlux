package com.springboot.client.service;

import com.springboot.client.SystemPrint;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GuiService {

    public static void uiManager() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        SwingUtilities.invokeLater(() -> createTray());
    }

    private static void createTray() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/icondruk2.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("PrinterLuxmed");

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("LuxmedPrinter");
        //Add components to popup menu
        popup.add(aboutItem);

        trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        trayIcon.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Wydruk Luxmed"));

        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Po więcej informacji skontaktuj się z administratorem"));
    }


    protected static Image createImage(String path, String description) {
        URL imageURL = SystemPrint.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
