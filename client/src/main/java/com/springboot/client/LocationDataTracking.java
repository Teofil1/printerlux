package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;


public class LocationDataTracking extends SystemPrint {

    public static void main(String[] args) {
        getDataLocation();
        cutDataOut();

        return;
    }

    private static void cutDataOut() {



    }

    public static void getDataLocation() {
        try (PowerShell powerShell = PowerShell.openSession()) {
            {

                PowerShellResponse response;
                response = powerShell.executeCommand("get-wmiobject -class win32_networkadapterconfiguration | Select-Object ipaddress");
                String location = response.getCommandOutput();
                System.out.println(location);

            }
        }


    }


}
