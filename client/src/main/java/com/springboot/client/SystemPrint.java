package com.springboot.client;

import com.springboot.client.service.GUIService;
import com.springboot.client.service.PrintService;

public class SystemPrint {
    public static void main(String[] args) {
        GUIService.uiManager();
        PrintService printService = new PrintService();
        printService.listenPrints();
    }
}
