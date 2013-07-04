package com.urbanairship.digitalwallet.client.http;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.*;

import java.io.IOException;
import java.util.Map;

public class HttpUtil {
    private static final String USER_AGENT = "PassTools Daemon Queue";
    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static String version = "1.2";
    private static String apiUrl = "http://localhost:8080/v1";
    private static String apiKey = "test";

    private static MultiThreadedHttpConnectionManager getConnectionManager() {

        if (connectionManager == null) {
            connectionManager = new MultiThreadedHttpConnectionManager();
            connectionManager.getParams().setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 256);
            connectionManager.getParams().setMaxTotalConnections(256);
        }

        return connectionManager;
    }

    public static void setConfig(String version, String apiKey, String apiUrl) {
        HttpUtil.version = version;
        HttpUtil.apiKey = apiKey;
        HttpUtil.apiUrl = apiUrl;
    }



    public static String get(String url, Map<String, String> parameters) throws IOException, HttpException {
        HttpClient client = new HttpClient(getConnectionManager());

        GetMethod method = new GetMethod(buildUrl(url, parameters));
        HttpUtil.setup(method);

        return executeMethod(client, method);
    }

    public static String post(String url, Map<String, String> parameters) throws IOException, HttpException {
        HttpClient client = new HttpClient(getConnectionManager());
        PostMethod method = new PostMethod(apiUrl + url);

        HttpUtil.setup(method);
        method.setParameter("api_key", apiKey);

        if (parameters != null) {
            for (Map.Entry<String, String> current : parameters.entrySet()) {
                method.setParameter(current.getKey(), current.getValue());
            }
        }

        return executeMethod(client, method);
    }

    public static String put(String url, String body, Map<String, String> parameters) throws IOException, HttpException {
        HttpClient client = new HttpClient(getConnectionManager());
        PutMethod method = new PutMethod(buildUrl(url, parameters));
        HttpUtil.setup(method);

        if (body != null) {
            method.setRequestEntity(new StringRequestEntity(body));
        }
        return executeMethod(client, method);
    }

    public static String delete(String url, Map<String, String> parameters) throws IOException, HttpException {
        HttpClient client = new HttpClient(getConnectionManager());
        String fullUrl = buildUrl(url, parameters);
        DeleteMethod method = new DeleteMethod(fullUrl);

        HttpUtil.setup(method);
        return executeMethod(client, method);
    }

    private static void setup(HttpMethod method) {
        method.setRequestHeader("User-Agent", USER_AGENT);
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        method.setRequestHeader("Api-Revision", HttpUtil.version);
        method.setRequestHeader("Accept","application/json");
    }

    private static boolean isOk(int sc) {
        return sc < 400;
    }

    private static String executeMethod(HttpClient client, HttpMethod method) throws IOException, HttpException {
        int sc;
        String response = "";
        try {
            sc = client.executeMethod(method);
            if (!HttpUtil.isOk(sc)) {
                String error = HttpException.errorMessage(sc);
                throw new HttpException(sc, error);
            }
        } finally {
            response = method.getResponseBodyAsString();
            method.releaseConnection();
        }
        return response;
    }

    public static String getBaseUrl() {
        return apiUrl;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getVersion() {
        return version;
    }

    /**
     * build the url for get, put and delete.
     *
     * @param url        URL to be appended to the hostname
     * @param parameters Parameters to be added to the url.
     * @return A string of the format http://localhost:8080/v1/[url]?api_key=XXXX&[parameters]
     */
    private static String buildUrl(String url, Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder(apiUrl).append(url).append("?api_key=").append(apiKey);
        if (parameters != null) {
            for (Map.Entry<String, String> current : parameters.entrySet()) {
                builder.append("&").append(current.getKey()).append("=").append(current.getValue());
            }
        }
        return builder.toString();
    }
}
