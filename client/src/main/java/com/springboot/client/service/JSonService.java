package com.springboot.client.service;

import com.springboot.client.model.PrintModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import com.google.gson.Gson;

public class JSonService {

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

    public static HttpURLConnection httpConnectToREST(String urlConn, String methodType, String requestProperty) throws IOException {
        URL url = new URL(urlConn);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userCredentials = "user:password";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        conn.setRequestProperty(requestProperty, basicAuth);
        conn.setDoOutput(true);
        conn.setRequestMethod(methodType);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }
}
