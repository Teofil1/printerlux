package com.springboot.client.service;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.DataFromBuffer;
import com.springboot.client.model.PrintModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Slf4j
public class PrintService {

    public static LinkedList<DataFromBuffer> getAllDocumentsFromPBuffer(PowerShell powerShell) {
        LinkedList<DataFromBuffer> allDocumentsFromBufferLinkedList = null;
        try {
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
            System.out.println(numberDocuments);
            if (numberDocuments > 3) {
                allDocumentsFromBufferLinkedList = new LinkedList<>();
                for (int i = 3; i < numberDocuments; i++) {
                    String owner = StringUtils.deleteWhitespace(owners.split("\n")[i]);
                    String document = StringUtils.deleteWhitespace(documents.split("\n")[i]);
                    Integer numberPagesPrinted = Integer.parseInt(StringUtils.deleteWhitespace(pagesprinted.split("\n")[i]));
                    Integer numberTotalPagesInDocument = Integer.parseInt(StringUtils.deleteWhitespace(totalpages.split("\n")[i]));
                    allDocumentsFromBufferLinkedList.add(DataFromBuffer.builder()
                            .owner(owner)
                            .document(document)
                            .pagesPrinted(numberPagesPrinted)
                            .totalPages(numberTotalPagesInDocument)
                            .build());
                }
                clearPrintedDocumentsFromBuffor(powerShell);
            }

        } catch (PowerShellNotAvailableException ex) {
            ex.printStackTrace();
        }
        return allDocumentsFromBufferLinkedList;
    }

    public static LinkedList<PrintModel> getPrintedDocuments(LinkedList<DataFromBuffer> allDocumentsFromBufferLinkedList) {
        LinkedList<PrintModel> printedDocumetsFromBufferLinkedList = null;
        if (allDocumentsFromBufferLinkedList != null) {
            printedDocumetsFromBufferLinkedList = new LinkedList<>();
            for (DataFromBuffer o : allDocumentsFromBufferLinkedList) {
                if (o.getTotalPages() == o.getPagesPrinted()) {
                    printedDocumetsFromBufferLinkedList.add(PrintModel.builder()
                            .owner(o.getOwner())
                            .nameDocument(o.getDocument())
                            .numberPages(o.getPagesPrinted())
                            .datePrint(LocalDateTime.now())
                            .build());
                }
            }
        }
        return printedDocumetsFromBufferLinkedList;
    }

    public static void addPrintPostRest(LinkedList<PrintModel> printedDocumetsFromBufferLinkedList) {
        if (printedDocumetsFromBufferLinkedList != null) {
            for (PrintModel o : printedDocumetsFromBufferLinkedList) {
                log.info("Dodanie wydruku: " + o);
                HttpURLConnection conn;
                try {
                    conn = JSonService
                            .httpConnectToREST("http://localhost:5050/printerlux/addPrint",
                                    "POST", "Authorization");
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

    private static void clearPrintedDocumentsFromBuffor(PowerShell powerShell) {
        powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
    }
}
