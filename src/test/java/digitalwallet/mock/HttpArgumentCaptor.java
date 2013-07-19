package digitalwallet.mock;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class HttpArgumentCaptor<T> {
    private final static String defaultHostName = "api.passtools.com";

    protected HttpClient httpClient;
    protected String body;
    protected ArgumentCaptor<T> captor;
    protected final Class<T> clazz;

    public HttpArgumentCaptor(Class<T> clazz, final HttpClient httpClient) throws IOException {
        this.clazz = clazz;
        captor = ArgumentCaptor.forClass(clazz);
        when(httpClient.execute((HttpUriRequest) captor.capture())).thenReturn(new MockHttpResponse());
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArgumentCaptor<T> getCaptor() {
        return captor;
    }

    public void setCaptor(ArgumentCaptor<T> captor) {
        this.captor = captor;
    }

    public void verify(final String path) {
        for (T current : captor.getAllValues()) {
            verify((HttpRequestBase)current, path);
        }
    }

    protected void verify(HttpRequestBase current, String address) {
        assert current.getURI().getPath().equals(address);
        assert current.getURI().getHost().equals(defaultHostName);
    }
}
