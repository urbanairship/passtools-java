package digitalwallet;

import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Template;
import digitalwallet.mock.MockHttpResponse;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class MockTemplateTest {
    private final static String defaultHostName = "api.passtools.com";
    private final static String defaultQueryParam = "api_key=YOUR_KEY";
    private final static String methodGet = "GET";

    @Mock
    private Template template;
    @Mock
    private HttpRequest request;
    @Mock
    private HttpClient httpClient;

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testGetTemplate() throws Exception {
        ArgumentCaptor<HttpGet> httpGetArgumentCaptor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(httpGetArgumentCaptor.capture())).thenReturn(new MockHttpResponse());

        Template.getTemplate(1);
        //verify(httpClient).execute(httpGetArgumentCaptor.capture());

        for (HttpGet current : httpGetArgumentCaptor.getAllValues()) {
            verify(current, "/v1/template/1");
        }
    }

    @org.testng.annotations.Test
    public void testGetTemplateExternal() throws Exception {
        ArgumentCaptor<HttpGet> httpGetArgumentCaptor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(httpGetArgumentCaptor.capture())).thenReturn(new MockHttpResponse());

        Template.getTemplate("ABC");

        for (HttpGet current : httpGetArgumentCaptor.getAllValues()) {
            verify(current, "/v1/template/id/ABC");
        }
    }

    private void verify(HttpRequestBase current, String address) {
        assert current.getURI().getPath().equals(address);
        assert current.getURI().getHost().equals(defaultHostName);
        assert current.getURI().getQuery().equals(defaultQueryParam);
        assert current.getMethod().equals(methodGet);
    }

    private Map<String, Object> randomHeaders() {
        Map<String, Object> headers = new HashMap<String, Object>();
        return headers;
    }

    private Map<String, Object> randomFields() {
        Map<String, Object> fields = new HashMap<String, Object>();
        return fields;
    }

    private String randomType() {
        TemplateTypeEnum t = TemplateTypeEnum.getRandomAppleTemplateType();
        return t.getJsonName();
    }

    /*
    private HttpResponse prepareResponse(int expectedResponseStatus, String expectedResponseBody) {
        HttpResponse response = new BasicHttpResponse(new BasicStatusLine( new ProtocolVersion("HTTP", 1, 1), expectedResponseStatus, "") );
        response.setStatusCode(expectedResponseStatus);
        try {
            response.setEntity(new StringEntity(expectedResponseBody));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
        return response;
    }
*/
}
