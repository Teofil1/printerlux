package com.springboot.client;

import com.springboot.client.model.DataFromBuffer;
import com.springboot.client.model.PrintModel;
import com.springboot.client.service.GuiService;
import com.springboot.client.service.PrintService;

import java.util.LinkedList;


public class SystemPrint {
    public static void main(String[] args) {
        GuiService.uiManager();
        while (true) {
            LinkedList<DataFromBuffer> dataFromBufferLinkedList = PrintService.getDataFromPBuffer();
            LinkedList<PrintModel> printModelLinkedList = PrintService.getPrintedDocuments(dataFromBufferLinkedList);
            PrintService.addPrintPostRest(printModelLinkedList);
            //PrintService.clearPrintedDocumentsFromBuffor();
        }
    }


}
