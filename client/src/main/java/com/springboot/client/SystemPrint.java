package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.springboot.client.model.LocationModel;
import com.springboot.client.service.GUIService;
import com.springboot.client.service.JSonService;
import com.springboot.client.service.LocationService;
import com.springboot.client.service.PrintService;

public class SystemPrint {
    public static void main(String[] args) {
        GUIService.uiManager();
        try {
            PowerShell powerShell = PowerShell.openSession();

            LocationService locationService = new LocationService(powerShell);
            LocationModel location = locationService.getLocationFromDB();

            PrintService printService = new PrintService(powerShell);
            printService.listenPrints(location);

        } catch (PowerShellNotAvailableException ex) {
            ex.printStackTrace();
        }


    }
}
