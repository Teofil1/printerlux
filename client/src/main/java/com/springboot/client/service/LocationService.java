package com.springboot.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.client.model.LocationModel;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Slf4j
public class LocationService {

    PowerShell powerShell;

    public LocationService(PowerShell powerShell) {
        this.powerShell = powerShell;
    }

    public LocationModel getLocationFromDB(){
        String myFirstTwoOctetsIpAddress = getFirstTwoOctetsIPAddressFromPowerShell();
        LocationModel locationModel = null;
        try {
            //locationModel = JSonService.getLocationModelFromDB(myFirstTwoOctetsIpAddress);
            locationModel = JSonService.getLocationModelFromDB("10.0.");
            System.out.println(locationModel);
            if (locationModel.equals(null)) {
                locationModel = LocationModel.builder()
                        .firstTwoOctetsIpAddress(getFirstTwoOctetsIPAddressFromPowerShell())
                        .build();
                createLocationPostRest(locationModel);
                locationModel = JSonService.getLocationModelFromDB(myFirstTwoOctetsIpAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationModel;
    }

    public String getFirstTwoOctetsIPAddressFromPowerShell() {
        String ipv4 = "";
        PowerShellResponse response;
        response = powerShell.executeCommand("get-wmiobject -class win32_networkadapterconfiguration | Select-Object ipaddress");
        String location = response.getCommandOutput();
        ipv4 = location.split("\\{")[1].split(",")[0];
        String firstOctet = ipv4.split("\\.")[0];
        String secondOctet = ipv4.split("\\.")[1];
        return firstOctet + "." + secondOctet + ".";
    }

    public void createLocationPostRest(LocationModel location) {
        //log.info("Dodanie lokalizacji: " + location);
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
