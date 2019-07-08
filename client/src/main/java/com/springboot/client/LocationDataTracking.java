package com.springboot.client;

import com.profesorfalken.jpowershell.PowerShell;

import com.profesorfalken.jpowershell.PowerShellResponse;

import java.util.StringTokenizer;


public class LocationDataTracking extends SystemPrint {

    public static String location;

    public static void main(String[] args) {

        getLocalizationFromPowerShell();
        cutDataFromPowerShell();

    }



    public static void getLocalizationFromPowerShell() {
        // wywołanie polecenia powershell do adresu ip, przekierowanie do zmiennej i zamknięcie sesji
        PowerShell locationOpen = PowerShell.openSession();
        {
            PowerShellResponse response;
            response = locationOpen.executeCommand("get-wmiobject -class win32_networkadapterconfiguration | Select-Object ipaddress");
            location = response.getCommandOutput();
           // System.out.println(location); //TODO wyciecic
            locationOpen.close();
        }

    }
    public static void cutDataFromPowerShell() {

        StringTokenizer stringTokenizer = new StringTokenizer(location, "{}");
        while(stringTokenizer.hasMoreTokens())
        {
            String klucz = stringTokenizer.nextToken();
            String wartosc = stringTokenizer.nextToken();
            location = wartosc;
            System.out.println(location); //TODO wyciać
        }


    }









}
