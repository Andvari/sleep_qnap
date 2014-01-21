package com.nemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        byte[] page = new byte[1000];

        URL url = new URL("http://192.168.1.2:8080/cgi-bin/authLogin.cgi?count=" + Math.random() + "&user=admin&pwd=YWRtaW4%3D&admin=1");

        HttpURLConnection conn;

        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.connect();

            InputStream istream = (InputStream) conn.getContent();
            int size_page = istream.read(page);
            page[size_page] = 0;
            String body = new String(page);
            String sid = new String(body.substring(body.indexOf("<authSid>") + 18, body.indexOf("</authSid>") - 3));
            url = new URL("http://192.168.1.2:8080/cgi-bin/sys/sysRequest.cgi?subfunc=power_mgmt&count=" + Math.random() + "&sid=" + sid + "&apply=shutdown");
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            istream = (InputStream) conn.getContent();
            istream.read(page);
        }
        catch(IOException e){
            System.out.println("Server does not responding");
            System.exit(-1);
        }
    }
}
