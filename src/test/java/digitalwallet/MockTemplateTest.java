package digitalwallet;

import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Template;
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

public class MockTemplateTest extends BaseMockTest {

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
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Template.getTemplate(1);
        //verify(httpClient).execute(httpGetArgumentCaptor.capture());

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/template/1");
        }
    }

    @org.testng.annotations.Test
    public void testGetTemplateExternal() throws Exception {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Template.getTemplate("ABC");

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/template/id/ABC");
        }
    }

    @org.testng.annotations.Test
    public void testCreate() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Template.createTemplate(TestHelper.randomName(), TestHelper.randomDescription(), randomType(), randomHeaders(), randomFields());

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/template");
        }
    }

    @org.testng.annotations.Test
    public void testCreateExternal() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());
        String externalId = randomExternalId();
        Template.createTemplate(externalId, TestHelper.randomName(), TestHelper.randomDescription(), randomType(), randomHeaders(), randomFields());

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/template/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testCreateInProject() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Template.createTemplate(1, TestHelper.randomName(), TestHelper.randomDescription(), randomType(), randomHeaders(), randomFields());

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/template/1");
        }
    }

    @org.testng.annotations.Test
    public void testHeaders() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Template.getMyTemplateHeaders();

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/template/headers");
        }
    }

    @org.testng.annotations.Test
    public void testDelete() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Template.delete(id);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/template/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testDeleteExternal() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String externalId = randomExternalId();
        Template.deleteX(externalId);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/template/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testUpdate() throws IOException {
        ArgumentCaptor<HttpPut> captor = ArgumentCaptor.forClass(HttpPut.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Template.updateTemplate(id, TestHelper.randomName(), TestHelper.randomDescription(), randomHeaders(), randomFields());

        for (HttpPut current : captor.getAllValues()) {
            verify(current, "/v1/template/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testUpdateExternal() throws IOException {
        ArgumentCaptor<HttpPut> captor = ArgumentCaptor.forClass(HttpPut.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String id = randomExternalId();
        Template.updateTemplate(id, TestHelper.randomName(), TestHelper.randomDescription(), randomHeaders(), randomFields());

        for (HttpPut current : captor.getAllValues()) {
            verify(current, "/v1/template/id/" + id);
        }
    }


}
