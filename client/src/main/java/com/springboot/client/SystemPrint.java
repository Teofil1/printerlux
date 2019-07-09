package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.DataFromBuffor;
import com.springboot.client.model.PrintModel;
import com.springboot.client.service.JSonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.LinkedList;


@Slf4j
public class SystemPrint {
    public static void main(String[] args) {
        uiManager();
        while(true){
            //try {
                addPrintPostRest();
                //clearPrintedDocumentsFromBuffor();

            /*}catch (Exception e){
                System.out.println("nie udalo sie");
            }*/
        }
    }

    public static LinkedList<DataFromBuffor> getDataFromPBuffor() {
        LinkedList<DataFromBuffor> listDataFromBuffor = null;
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
            if (numberDocuments > 3) {
                listDataFromBuffor = new LinkedList<>();
                for (int i = 3; i < numberDocuments; i++) {
                    String owner = StringUtils.deleteWhitespace(owners.split("\n")[i]);
                    String document = StringUtils.deleteWhitespace(documents.split("\n")[i]);
                    Integer numberPagesPrinted = Integer.parseInt(StringUtils.deleteWhitespace(pagesprinted.split("\n")[i]));
                    Integer numberTotalPagesInDocument = Integer.parseInt(StringUtils.deleteWhitespace(totalpages.split("\n")[i]));
                    listDataFromBuffor.add(new DataFromBuffor(owner, document, numberPagesPrinted, numberTotalPagesInDocument));
                }
            }
        } catch (PowerShellNotAvailableException ex) {
            ex.printStackTrace();
        }
        return listDataFromBuffor;
    }

    public static LinkedList<PrintModel> getPrintedDocuments() {
        LinkedList<DataFromBuffor> listDataFromBuffer = getDataFromPBuffor();
        LinkedList<PrintModel> listPrintedDocuments = null;
        if(listDataFromBuffer != null) {
            listPrintedDocuments = new LinkedList<>();
            for (DataFromBuffor o : listDataFromBuffer ) {
                if (o.getTotalPages()==o.getPagesPrinted()) {
                    listPrintedDocuments.add(new PrintModel(o.getOwner(), o.getDocument(), o.getPagesPrinted()));
                }
            }
        }
        return listPrintedDocuments;
    }

    public static void addPrintPostRest() {
        LinkedList<PrintModel> listPrintedDocuments = getPrintedDocuments();
        if(listPrintedDocuments!=null) {
            for (PrintModel o : listPrintedDocuments) {
                log.info("Dodanie wydruku: " + o);
                HttpURLConnection conn;
                try {
                    conn = JSonService
                            .httpConnectToREST("http://localhost:5050/printerlux/addPrint",
                                    "POST");
                    JSonService.addParsedJsonObject(o, conn);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else System.out.println("Nie ma w buforze dokumentow do wysylania");
    }

    private static void clearPrintedDocumentsFromBuffor() {
        PowerShell powerShell = PowerShell.openSession();
        powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
    }

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
        SwingUtilities.invokeLater(() -> createAndShowGUI());
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
        trayIcon.setToolTip("PrinterLuxmed");

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
