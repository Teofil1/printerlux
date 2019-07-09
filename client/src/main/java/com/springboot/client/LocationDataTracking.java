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
        // przycięcie stringa do postaci ip i adresu mac
        StringTokenizer cutStrang1 = new StringTokenizer(location, "{}");
        while(cutStrang1.hasMoreTokens())
        {
            String key1 = cutStrang1.nextToken();
            String value1 = cutStrang1.nextToken();
            location = value1;
           // System.out.println(location); //TODO wyciać
        }
        // przyciecie stringa do postaci samego adresu ip komputera
        StringTokenizer cutStrang2 = new StringTokenizer(location, ",");
        while(cutStrang2.hasMoreTokens())
        {
            String key2 = cutStrang2.nextToken();
            String value2 = cutStrang2.nextToken();
            location = key2;
            System.out.println(location); //TODO wyciać
        }
    }









}
