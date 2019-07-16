package com.springboot.client.service;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.LocationModel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;

@Slf4j
public class LocationService {

    PowerShell powerShell;

    public LocationService(PowerShell powerShell) {
        this.powerShell = powerShell;
    }

    public LocationModel getLocationFromDB() {
        String myFirstTwoOctetsIpAddress = getFirstTwoOctetsIPAddressFromPowerShell();
        LocationModel locationModel = null;
        try {
            locationModel = JSonService.getLocationModelGetRest(myFirstTwoOctetsIpAddress);
            if (locationModel == null) {
                locationModel = LocationModel.builder()
                        .firstTwoOctetsIpAddress(getFirstTwoOctetsIPAddressFromPowerShell())
                        .build();
                createLocationPostRest(locationModel);
                locationModel = JSonService.getLocationModelGetRest(myFirstTwoOctetsIpAddress);
            }
            log.info("Lokalizacja: " + locationModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationModel;
    }

    public String getFirstTwoOctetsIPAddressFromPowerShell() {
        String ipv4 = getIPAddressFromPowerShell();
        String firstOctet = ipv4.split("\\.")[0];
        String secondOctet = ipv4.split("\\.")[1];
        return firstOctet + "." + secondOctet + ".";
    }

    private String getIPAddressFromPowerShell() {
        String ipv4 = "";
        PowerShellResponse response;
        response = powerShell.executeCommand("get-wmiobject -class win32_networkadapterconfiguration | Select-Object ipaddress");
        String location = response.getCommandOutput();
        ipv4 = location.split("\\{")[1].split(",")[0];
        return ipv4;
    }


    public void createLocationPostRest(LocationModel location) {
        HttpURLConnection conn;
        try {
            conn = JSonService
                    .httpConnectToREST("http://localhost:5050/printerlux/createLocation",
                            "POST", "Authorization");
            JSonService.addParsedJsonObject(location, conn);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
