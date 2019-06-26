package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.PrintModel;
import com.springboot.client.service.JSonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


@Slf4j
public class SystemPrint {
    public static void main(String[] args) {

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
        SwingUtilities.invokeLater(() -> createAndShowGUI());

        ArrayList<PrintModel> arrayPrint;

        while(true) {

            System.out.println("wykonuje sie");

            try (PowerShell powerShell = PowerShell.openSession()) {

                PowerShellResponse response;

                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Owner");
                String owners = response.getCommandOutput();
                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Document");
                String documents = response.getCommandOutput();
                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object PagesPrinted");
                String pagesprinted = response.getCommandOutput();
                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object TotalPages");
                String totalpages = response.getCommandOutput();

                int numberDocuments = documents.split("\n").length;

                if(numberDocuments>3) {
                    arrayPrint = new ArrayList<>();
                    for (int i = 3; i < numberDocuments; i++) {
                        arrayPrint.add(new PrintModel(StringUtils.deleteWhitespace(owners.split("\n")[i]), StringUtils.deleteWhitespace(documents.split("\n")[i]),
                                StringUtils.deleteWhitespace(pagesprinted.split("\n")[i]), StringUtils.deleteWhitespace(totalpages.split("\n")[i])));
                    }
                    for (PrintModel o: arrayPrint) {
                        if(o.getTotalPages().equals(o.getPagesPrinted())){

                            log.info("Dodanie wydruku: " + o);
                            HttpURLConnection conn;
                            try {
                                conn = JSonService
                                        .httpConnectToREST("http://localhost:5050/demo/addPrint",
                                                "POST",
                                                "Authorization");
                                    JSonService.addParsedJsonObject(o, conn);
                            } catch (IOException e1) {
                                e1.printStackTrace();

                            }
                        }
                    }
                    powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
                }
            } catch (PowerShellNotAvailableException ex) {
            }
        }
    }

    private static void createAndShowGUI() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/icondruk2.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("PrinterLux");

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
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
                "This dialog box is run from System Tray"));

        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "I'm watching you! Ha-ha!"));
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
