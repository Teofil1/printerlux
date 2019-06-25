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
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


@Slf4j
public class SystemPrint {
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(() -> createAndShowGUI());

        ArrayList<PrintModel> arrayPrintDTO;

        while(true) {

            System.out.println("ywkonuje sie");

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
                arrayPrintDTO = new ArrayList<>();

                if(numberDocuments>3) {
                    for (int i = 3; i < numberDocuments; i++) {
                        arrayPrintDTO.add(new PrintModel(StringUtils.deleteWhitespace(owners.split("\n")[i]), StringUtils.deleteWhitespace(documents.split("\n")[i]),
                                StringUtils.deleteWhitespace(pagesprinted.split("\n")[i]), StringUtils.deleteWhitespace(totalpages.split("\n")[i])));
                    }
                    for (PrintModel o: arrayPrintDTO) {
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
                            //printService.addPrint(o);
                            //Tu wysylamy PrintModel do Serwera
                        }
                    }
                    powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
                }
            } catch (PowerShellNotAvailableException ex) {
            }
        }



    }

    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/icondruk.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a popup menu components
        /*MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");*/
        Menu displayMenu = new Menu("Display");
        /*MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");*/

        //Add components to popup menu
        /*popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();*/
        popup.add(displayMenu);
        /*displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);*/

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "This dialog box is run from System Tray"));

        /*aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "This dialog box is run from the About menu item"));

        cb1.addItemListener(e -> {
            int cb1Id = e.getStateChange();
            if (cb1Id == ItemEvent.SELECTED){
                trayIcon.setImageAutoSize(true);
            } else {
                trayIcon.setImageAutoSize(false);
            }
        });

        cb2.addItemListener(e -> {
            int cb2Id = e.getStateChange();
            if (cb2Id == ItemEvent.SELECTED){
                trayIcon.setToolTip("Sun TrayIcon");
            } else {
                trayIcon.setToolTip(null);
            }
        });*/

        ActionListener listener = e -> {
            MenuItem item = (MenuItem)e.getSource();
            //TrayIcon.MessageType type = null;
            System.out.println(item.getLabel());
            if ("Error".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.ERROR;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is an error message", TrayIcon.MessageType.ERROR);

            } else if ("Warning".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.WARNING;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is a warning message", TrayIcon.MessageType.WARNING);

            } else if ("Info".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.INFO;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is an info message", TrayIcon.MessageType.INFO);

            } else if ("None".equals(item.getLabel())) {
                //type = TrayIcon.MessageType.NONE;
                trayIcon.displayMessage("Sun TrayIcon Demo",
                        "This is an ordinary message", TrayIcon.MessageType.NONE);
            }
        };

        /*errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });*/
    }

    //Obtain the image URL
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
