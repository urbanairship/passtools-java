package com.passtools.client;


import com.passtools.client.exception.*;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class PassToolsClient {


    private static void handleAPIError(String responseBody, int responseCode) throws PassToolsException {
        switch (responseCode) {
            case 400:
                throw new InvalidRequestException(responseBody);
            case 401:
                throw new AuthenticationException(responseBody);
            case 406:
                throw new InvalidRequestException(responseBody);
            case 429:
                throw new TooManyRequestsException(responseBody);
            case 500:
                throw new InternalServerException(responseBody);
            default:
                throw new ApiException(responseBody);
        }

    }


    protected static void apiKeyCheck() throws AuthenticationException {
        if ((PassTools.apiKey == null || PassTools.apiKey.length() == 0) && (PassTools.apiKey == null || PassTools.apiKey.length() == 0)) {
            throw new AuthenticationException("No API secret key provided.");
        }
    }

    protected static String addApiKey(String url) throws Exception {
        return url + "?api_key=" + URLEncoder.encode(PassTools.apiKey, "UTF-8");
    }


    protected static void handleError(HttpResponse response) throws IOException, PassToolsException {
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode < 200 || responseCode >= 300) {
            String responseStr = EntityUtils.toString(response.getEntity());
            handleAPIError(responseStr, responseCode);

        }
    }

    protected static void setJsonFormField(List<NameValuePair> postParams, Map formFields) throws Exception {
        if (formFields != null && formFields.size() > 0) {

            Object o = (Object) formFields.get("json");

            if (o instanceof JSONAware) {
                postParams.add(new BasicNameValuePair("json", ((JSONAware) o).toJSONString()));
            } else {
                throw new IllegalArgumentException("please pass a JSONObject or JSONArray value into the form fields");
            }
        }

    }


    protected static HttpClient getHttpClient() throws Exception {
        HttpClient base = new DefaultHttpClient();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {

            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = base.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", ssf, 443));


        HttpClient httpClient = new DefaultHttpClient(ccm, base.getParams());
        return httpClient;


    }


    protected static PassToolsResponse _rawGet(String url) throws Exception {


        HttpClient httpclient = getHttpClient();
        HttpGet get = new HttpGet(url);

        HttpResponse response = httpclient.execute(get);

        handleError(response);

        return new PassToolsResponse(response);

    }


    protected static PassToolsResponse get(String url) throws Exception {

        apiKeyCheck();

        HttpClient httpclient = getHttpClient();
        HttpGet get = new HttpGet(addApiKey(url));

        HttpResponse response = httpclient.execute(get);

        handleError(response);

        return new PassToolsResponse(response);

    }


    protected static Map defaultHeaders() {
        Map headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        return headers;
    }

    private static void setHeaders(HttpMessage message, Map headers) {

        if (headers != null || headers.size() > 0) {
            Iterator it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = (String) headers.get(key);
                message.setHeader(key, value);

            }
        }

    }


    protected static PassToolsResponse post(String url, Map formFields, Map headers) throws Exception {

        apiKeyCheck();


        HttpClient httpclient = getHttpClient();
        HttpPost post = new HttpPost(url);

        setHeaders(post, headers);


        List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        setJsonFormField(postParams, formFields);
        postParams.add(new BasicNameValuePair("api_key", URLEncoder.encode(PassTools.apiKey, "UTF-8")));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
        entity.setContentEncoding(HTTP.UTF_8);
        post.setEntity(entity);

        HttpResponse response = httpclient.execute(post);

        handleError(response);

        return new PassToolsResponse(response);

    }


    protected static PassToolsResponse post(String url, Map formFields) throws Exception {
        return post(url, formFields, defaultHeaders());
    }


    protected static PassToolsResponse _put(String url, Map formFields, Map headers, List<BasicNameValuePair> queryParams) throws Exception {

        apiKeyCheck();


        HttpClient httpclient = getHttpClient();
        HttpPut put = new HttpPut(url);

        setHeaders(put, headers);


        List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        setJsonFormField(postParams, formFields);
        postParams.add(new BasicNameValuePair("api_key", URLEncoder.encode(PassTools.apiKey, "UTF-8")));

        if (queryParams.size() > 0) {
            for (BasicNameValuePair queryParam : queryParams) {
                postParams.add(queryParam);
            }
        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
        entity.setContentEncoding(HTTP.UTF_8);
        put.setEntity(entity);


        HttpResponse response = httpclient.execute(put);

        handleError(response);

        return new PassToolsResponse(response);

    }


    protected static PassToolsResponse put(String url, Map formFields) throws Exception {
        return _put(url, formFields, defaultHeaders(), new ArrayList<BasicNameValuePair>());
    }


    protected static PassToolsResponse putWithParams(String url, Map formFields, List<BasicNameValuePair> queryParams) throws Exception {
        return _put(url, formFields, defaultHeaders(), queryParams);
    }


    protected static PassToolsResponse delete(String url) throws Exception {
        return delete(url, Collections.emptyMap());
    }

    protected static PassToolsResponse delete(String url, Map headers) throws Exception {
        apiKeyCheck();

        HttpClient httpclient = getHttpClient();
        HttpDelete delete = new HttpDelete(addApiKey(url));

        setHeaders(delete, headers);


        HttpResponse response = httpclient.execute(delete);

        handleError(response);

        return new PassToolsResponse(response);


    }


}
