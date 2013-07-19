package com.urbanairship.digitalwallet.client;


import org.apache.http.client.HttpClient;

public abstract class PassTools {
    public static String API_BASE = "https://api.passtools.com/v1";
    public static final String VERSION = "1.2";
    public static String apiKey;    //this is your given secret key
    public static HttpClient client;        /* used for mock testing */
}
