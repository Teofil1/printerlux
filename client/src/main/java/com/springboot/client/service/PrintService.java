package com.springboot.client.service;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.DataFromBuffor;
import com.springboot.client.model.PrintModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.LinkedList;

@Slf4j
public class PrintService {

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
        if (listDataFromBuffer != null) {
            listPrintedDocuments = new LinkedList<>();
            for (DataFromBuffor o : listDataFromBuffer) {
                if (o.getTotalPages() == o.getPagesPrinted()) {
                    listPrintedDocuments.add(new PrintModel(o.getOwner(), o.getDocument(), o.getPagesPrinted(), new Date().toString()));
                }
            }
        }
        return listPrintedDocuments;
    }

    public static void addPrintPostRest() {
        LinkedList<PrintModel> listPrintedDocuments = getPrintedDocuments();
        if (listPrintedDocuments != null) {
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
    }

    public static String getIPFromPowerShell() {
        String ipv4 = "";
        try (PowerShell powerShell = PowerShell.openSession()) {
            PowerShellResponse response;
            response = powerShell.executeCommand("get-wmiobject -class win32_networkadapterconfiguration | Select-Object ipaddress");
            String location = response.getCommandOutput();
            ipv4 = location.split("\\{")[1].split(",")[0];
        } catch (PowerShellNotAvailableException ex) {
        }
        return ipv4;
    }

    public static void clearPrintedDocumentsFromBuffor() {
        PowerShell powerShell = PowerShell.openSession();
        powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
    }
}
