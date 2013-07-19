package digitalwallet;


import com.urbanairship.digitalwallet.client.Pass;
import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;
import digitalwallet.mock.MockHttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class MockPassTest extends BaseMockTest {
    @Mock
    private HttpClient httpClient;

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testListPasses() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Pass.listPasses(10, 1);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/pass");
        }
    }

    @org.testng.annotations.Test
    public void testGetPass() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Pass.get(id);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/pass/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testGetPassExternal() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String externalId = randomExternalId();
        Pass.getPass(externalId);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/pass/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testCreatePass() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long templateId = randomId();
        Pass.create(templateId, randomFields());

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/pass/" + templateId);
        }
    }

    @org.testng.annotations.Test
    public void testCreatePassExternal() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long templateId = randomId();
        String externalId = randomExternalId();
        Pass.create(templateId, externalId, randomFields());

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/pass/" + templateId + "/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testCreatePassExternalX() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String templateExternalId = randomExternalId();
        String externalId = randomExternalId();
        Pass.create(templateExternalId, externalId, randomFields());

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/pass/id/" + templateExternalId + "/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testUpdatePass() throws IOException {
        ArgumentCaptor<HttpPut> captor = ArgumentCaptor.forClass(HttpPut.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Pass.update(id, randomFields());

        for (HttpPut current : captor.getAllValues()) {
            verify(current, "/v1/pass/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testUpdatePassExternal() throws IOException {
        ArgumentCaptor<HttpPut> captor = ArgumentCaptor.forClass(HttpPut.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String externalId = randomExternalId();
        Pass.update(externalId, randomFields());

        for (HttpPut current : captor.getAllValues()) {
            verify(current, "/v1/pass/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testDeletePass() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Pass.delete(id);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/pass/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testDeletePassExternal() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String externalId = randomExternalId();
        Pass.deleteX(externalId);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/pass/id/" + externalId);
        }
    }
}
