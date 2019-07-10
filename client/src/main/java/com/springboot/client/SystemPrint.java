package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;
import com.springboot.client.model.DataFromBuffer;
import com.springboot.client.model.PrintModel;
import com.springboot.client.service.GuiService;
import com.springboot.client.service.PrintService;

import java.util.LinkedList;


public class SystemPrint {
    public static void main(String[] args) {
        GuiService.uiManager();
        PowerShell powerShell = PowerShell.openSession();
        while (true) {
            LinkedList<DataFromBuffer> allDocumentsFromPBufferLinkedList = PrintService.getAllDocumentsFromPBuffer(powerShell);
            LinkedList<PrintModel> printedDocumentsFromBufferLinkedList = PrintService.getPrintedDocuments(allDocumentsFromPBufferLinkedList);
            PrintService.addPrintPostRest(printedDocumentsFromBufferLinkedList);
        }
    }


}
