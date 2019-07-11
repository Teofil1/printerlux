package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;
import com.springboot.client.model.DataSingleDocumentFromBuffer;
import com.springboot.client.model.PrintModel;
import com.springboot.client.service.GUIService;
import com.springboot.client.service.PrintService;

import java.util.LinkedList;


public class SystemPrint {
    public static void main(String[] args) {
        GUIService.uiManager();
        PowerShell powerShell = PowerShell.openSession();
        while (true) {
            int numberDocuments = PrintService.getNumberDocumentsInBuffer(powerShell);
            if(numberDocuments>0) {
                LinkedList<DataSingleDocumentFromBuffer> allDocumentsFromPBufferLinkedList = PrintService.getDataAllDocumentsFromPBuffer(powerShell, numberDocuments);
                LinkedList<PrintModel> printedDocumentsFromBufferLinkedList = PrintService.getPrintedDocuments(allDocumentsFromPBufferLinkedList);
                PrintService.addPrintPostRest(printedDocumentsFromBufferLinkedList);
            }
        }
    }


}
