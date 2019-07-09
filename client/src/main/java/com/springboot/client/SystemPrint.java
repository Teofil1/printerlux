package com.springboot.client;

import com.springboot.client.service.GuiService;
import com.springboot.client.service.PrintService;


public class SystemPrint {
    public static void main(String[] args) {
        GuiService.uiManager();
        while (true) {
            PrintService.addPrintPostRest();
            //PrintService.clearPrintedDocumentsFromBuffor();
        }
    }


}
