package digitalwallet;


import digitalwallet.mock.HttpArgumentCaptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.mockito.Mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseMockTest {

    @Mock
    protected HttpClient httpClient;

    protected HttpArgumentCaptor<HttpGet> getGetCaptor() throws IOException {
        return new HttpArgumentCaptor<HttpGet>(HttpGet.class, httpClient);
    }

    protected HttpArgumentCaptor<HttpPost> getPostCaptor() throws IOException {
        return new HttpArgumentCaptor<HttpPost>(HttpPost.class, httpClient);
    }

    protected HttpArgumentCaptor<HttpPut> getPutCaptor() throws IOException {
        return new HttpArgumentCaptor<HttpPut>(HttpPut.class, httpClient);
    }


    protected HttpArgumentCaptor<HttpDelete> getDeleteCaptor() throws IOException {
        return new HttpArgumentCaptor<HttpDelete>(HttpDelete.class, httpClient);
    }

    protected Map<String, Object> randomHeaders() {
        return new HashMap<String, Object>();
    }

    protected Map<String, Object> randomFields() {
        return new HashMap<String, Object>();
    }

    protected String randomType() {
        TemplateTypeEnum t = TemplateTypeEnum.getRandomAppleTemplateType();
        return t.getJsonName();
    }

    protected long randomId() {
        return (long)(Math.random() * 1000000);
    }

    protected String randomExternalId() {
        return TestHelper.randomString("External-");
    }

    protected abstract String getBaseUrl();

    protected String getBaseUrl(long id) {
        StringBuilder builder = new StringBuilder(getBaseUrl());
        builder.append('/').append(id);
        return builder.toString();
    }

    protected String getBaseUrl(String externalId) {
        StringBuilder builder = new StringBuilder(getBaseUrl());
        builder.append("/id/").append(externalId);
        return builder.toString();
    }
}
