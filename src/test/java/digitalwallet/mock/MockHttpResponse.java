package digitalwallet.mock;

import org.apache.http.*;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MockHttpResponse implements HttpResponse {
    private MockStatusLine statusLine;
    private MockEntity entity;
    private Locale locale = Locale.ENGLISH;

    private HeaderGroup headers;
    private HttpParams params;

    public MockHttpResponse() {
        statusLine = new MockStatusLine();
        entity = new MockEntity();
        headers = new HeaderGroup();
    }

    public MockHttpResponse(final String body) {
        statusLine = new MockStatusLine();
        entity = new MockEntity(body);
        headers = new HeaderGroup();
    }

    @Override
    public StatusLine getStatusLine() {
        return statusLine;
    }

    @Override
    public void setStatusLine(final StatusLine statusLine) {
        this.statusLine = new MockStatusLine(statusLine.getProtocolVersion(), statusLine.getReasonPhrase(), statusLine.getStatusCode());
    }

    @Override
    public void setStatusLine(final ProtocolVersion protocolVersion, int i) {
        this.statusLine = new MockStatusLine(protocolVersion, i);
    }

    @Override
    public void setStatusLine(final ProtocolVersion protocolVersion, int i, final String s) {
        this.statusLine = new MockStatusLine(protocolVersion, s, i);
    }

    @Override
    public void setStatusCode(int i) throws IllegalStateException {
        this.statusLine.setStatusCode(i);
    }

    @Override
    public void setReasonPhrase(final String s) throws IllegalStateException {
        this.statusLine.setReasonPhrase(s);
    }

    @Override
    public HttpEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(final HttpEntity httpEntity) {
        // do we want to implement this?
        if (httpEntity != null) {
            try {
                entity = new MockEntity(getStringFromInputStream(httpEntity.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
                entity = new MockEntity();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return statusLine.getProtocolVersion();
    }

    @Override
    public boolean containsHeader(final String s) {
        return headers.containsHeader(s);
    }

    @Override
    public Header[] getHeaders(final String s) {
        return headers.getHeaders(s);
    }

    @Override
    public Header getFirstHeader(final String s) {
        return headers.getFirstHeader(s);
    }

    @Override
    public Header getLastHeader(final String s) {
        return headers.getLastHeader(s);
    }

    @Override
    public Header[] getAllHeaders() {
        return headers.getAllHeaders();
    }

    @Override
    public void addHeader(final Header header) {
        headers.addHeader(header);
    }

    @Override
    public void addHeader(final String name, final String value) {
        addHeader(new BasicHeader(name, value));
    }

    @Override
    public void setHeader(final Header header) {
        headers.updateHeader(header);
    }

    @Override
    public void setHeader(final String name, final String value) {
        if (name != null) {
            headers.updateHeader(new BasicHeader(name, value));
        } else {
            throw new IllegalArgumentException("Header name may not be null");
        }
    }

    @Override
    public void setHeaders(final Header[] headers) {
        this.headers.setHeaders(headers);
    }

    @Override
    public void removeHeader(final Header header) {
        this.headers.removeHeader(header);
    }

    @Override
    public void removeHeaders(final String name) {
        if (name != null) {
            for (HeaderIterator i = this.headers.iterator(); i.hasNext(); ) {
                Header header = i.nextHeader();
                if (name.equalsIgnoreCase(header.getName())) {
                    i.remove();
                }
            }
        }
    }

    @Override
    public HeaderIterator headerIterator() {
        return this.headers.iterator();
    }

    @Override
    public HeaderIterator headerIterator(String name) {
        return this.headers.iterator(name);
    }

    @Override
    public HttpParams getParams() {
        if (params == null) {
            params = new BasicHttpParams();
        }
        return params;
    }

    @Override
    public void setParams(final HttpParams params) {
        if (params == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        this.params = params;
    }

    // flagrantly stolen from the internet ;)
    private String getStringFromInputStream(final InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
