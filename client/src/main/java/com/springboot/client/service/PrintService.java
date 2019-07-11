package com.springboot.client.service;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.DataSingleDocumentFromBuffer;
import com.springboot.client.model.PrintModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Slf4j
public class PrintService {

    public static LinkedList<DataSingleDocumentFromBuffer> getDataAllDocumentsFromPBuffer(PowerShell powerShell, int numberDocuments) {
        LinkedList<DataSingleDocumentFromBuffer> allDocumentsFromBufferLinkedList = null;
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
            allDocumentsFromBufferLinkedList = new LinkedList<>();
            for (int i = 0; i < numberDocuments; i++) {
                String owner = StringUtils.deleteWhitespace(owners.split("\n")[i + 3]);
                String document = StringUtils.deleteWhitespace(documents.split("\n")[i + 3]);
                Integer numberPagesPrinted = Integer.parseInt(StringUtils.deleteWhitespace(pagesprinted.split("\n")[i + 3]));
                Integer numberTotalPagesInDocument = Integer.parseInt(StringUtils.deleteWhitespace(totalpages.split("\n")[i + 3]));
                allDocumentsFromBufferLinkedList.add(DataSingleDocumentFromBuffer.builder()
                        .owner(owner)
                        .document(document)
                        .pagesPrinted(numberPagesPrinted)
                        .totalPages(numberTotalPagesInDocument)
                        .build());
            }
            clearPrintedDocumentsFromBuffor(powerShell);
        } catch (PowerShellNotAvailableException ex) {
            ex.printStackTrace();
        }
        return allDocumentsFromBufferLinkedList;
    }

    public static int getNumberDocumentsInBuffer(PowerShell powerShell) {
        try {
            PowerShellResponse response;
            response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Document");
            String documents = response.getCommandOutput();
            return documents.split("\n").length - 3;
        } catch (PowerShellNotAvailableException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static LinkedList<PrintModel> getPrintedDocuments(LinkedList<DataSingleDocumentFromBuffer> allDocumentsFromBufferLinkedList) {
        LinkedList<PrintModel> printedDocumetsFromBufferLinkedList = null;
        if (allDocumentsFromBufferLinkedList != null) {
            printedDocumetsFromBufferLinkedList = new LinkedList<>();
            for (DataSingleDocumentFromBuffer o : allDocumentsFromBufferLinkedList) {
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

    public static String getIPFromPowerShell(PowerShell powerShell) {
        String ipv4 = "";
        try {
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
