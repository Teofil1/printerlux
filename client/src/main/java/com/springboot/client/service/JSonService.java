package com.springboot.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.client.model.PrintModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;

public class JSonService {

    /*public static List<PrintModel> getListOfModels() throws IOException {
        URL url = new URL("http://localhost:5050/printerlux/print");//your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        String userCredentials = "user:password";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        conn.setRequestProperty("Authorization", basicAuth);

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;

        //parse to list of objects <PrintModel>, create service printmodel, extract
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        output = br.readLine();
        List<PrintModel> listOfCurseModel = mapper.readValue(output, new TypeReference<List<PrintModel>>() {
        });
        conn.disconnect();
        return listOfCurseModel;
    }*/

    public static void addParsedJsonObject(PrintModel printModel, HttpURLConnection conn) throws IOException {
        Gson gson = new Gson();
        String input = gson.toJson(printModel);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        conn.disconnect();
    }

    public static HttpURLConnection httpConnectToREST(String urlConn, String methodType) throws IOException {
        URL url = new URL(urlConn);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:password";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        //conn.setRequestProperty(requestProperty, basicAuth);
        conn.setDoOutput(true);
        conn.setRequestMethod(methodType);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    /*public static HttpURLConnection httpConnectToREST(String urlConn, String methodType, String requestProperty) throws IOException {
        URL url = new URL(urlConn);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:password";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        System.out.println(basicAuth);
        conn.setRequestProperty(requestProperty, basicAuth);
        conn.setDoOutput(true);
        conn.setRequestMethod(methodType);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }*/
}
