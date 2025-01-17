package com.springboot.client.service;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.DataSingleDocumentFromBuffer;
import com.springboot.client.model.LocationModel;
import com.springboot.client.model.PrintModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Slf4j
public class PrintService {

    PowerShell powerShell;

    public PrintService(PowerShell powerShell) {
        this.powerShell = powerShell;
    }


    public void listenPrints(LocationModel location) {
        while (true) {
            int numberDocuments = getNumberDocumentsInBuffer();
            System.out.println("Liczba wszystkich dokumentow w buforze:" + numberDocuments);
            if (numberDocuments > 0) {
                LinkedList<DataSingleDocumentFromBuffer> allDocumentsFromPBufferLinkedList = getDataAllDocumentsFromPBuffer(numberDocuments);
                LinkedList<PrintModel> printedDocumentsFromBufferLinkedList = getPrintedDocuments(allDocumentsFromPBufferLinkedList, location);
                addPrintPostRest(printedDocumentsFromBufferLinkedList);
            }
        }
    }

    public LinkedList<DataSingleDocumentFromBuffer> getDataAllDocumentsFromPBuffer(int numberDocuments) {
        PowerShellResponse response;
        response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Owner");
        String owners = response.getCommandOutput();
        response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Document");
        String documents = response.getCommandOutput();
        response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object PagesPrinted");
        String pagesprinted = response.getCommandOutput();
        response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object TotalPages");
        String totalpages = response.getCommandOutput();
        LinkedList<DataSingleDocumentFromBuffer> allDocumentsFromBufferLinkedList = new LinkedList<>();
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
        clearPrintedDocumentsFromBuffor();
        return allDocumentsFromBufferLinkedList;
    }

    public int getNumberDocumentsInBuffer() {
        PowerShellResponse response;
        response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Document");
        String documents = response.getCommandOutput();
        if(documents.split("\n").length - 3 == -2) return 0;
        else return documents.split("\n").length - 3;
    }

    public LinkedList<PrintModel> getPrintedDocuments(LinkedList<DataSingleDocumentFromBuffer> allDocumentsFromBufferLinkedList, LocationModel location) {
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
                            .location(location)
                            .build());
                }
            }
        }
        return printedDocumetsFromBufferLinkedList;
    }

    public void addPrintPostRest(LinkedList<PrintModel> printedDocumetsFromBufferLinkedList) {
        if (printedDocumetsFromBufferLinkedList != null) {
            for (PrintModel o : printedDocumetsFromBufferLinkedList) {
                HttpURLConnection conn;
                try {
                    conn = JSonService
                            .httpConnectToREST("http://localhost:5050/printerlux/addPrint",
                                    "POST", "Authorization");
                    JSonService.addParsedJsonObject(o, conn);
                    log.info("Dodanie wydruku: " + o);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    public void clearPrintedDocumentsFromBuffor() {
        powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
    }
}
