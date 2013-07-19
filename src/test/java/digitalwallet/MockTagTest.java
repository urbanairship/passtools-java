package digitalwallet;


import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;
import digitalwallet.mock.MockHttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class MockTagTest extends BaseMockTest {
    @Mock
    private HttpClient httpClient;

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testListTag() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Tag.getList(10, 1);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/tag");
        }
    }

    @org.testng.annotations.Test
    public void testGetPasses() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String tag = TestHelper.randomTag();
        Tag.getPasses(tag, 10, 1);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/tag/" + tag + "/passes");
        }
    }

    @org.testng.annotations.Test
    public void testUpdateTag() throws IOException {
        ArgumentCaptor<HttpPut> captor = ArgumentCaptor.forClass(HttpPut.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String tag = TestHelper.randomTag();
        Tag.updatePasses(tag, randomFields());

        for (HttpPut current : captor.getAllValues()) {
            verify(current, "/v1/tag/" + tag + "/passes");
        }
    }

    @org.testng.annotations.Test
    public void testDeleteTag() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String tag = TestHelper.randomTag();
        Tag.deleteTag(tag);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/tag/" + tag);
        }
    }

    @org.testng.annotations.Test
    public void testRemoveFromPass() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String tag = TestHelper.randomTag();
        long id = randomId();
        Tag.removeFromPass(tag, id);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/tag/" + tag + "/pass/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testRemoveFromPassExternal() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String tag = TestHelper.randomTag();
        String externalId = randomExternalId();
        Tag.removeFromPass(tag, externalId);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/tag/" + tag + "/pass/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testRemoveFromPasses() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String tag = TestHelper.randomTag();
        Tag.removeFromPasses(tag);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/tag/" + tag + "/passes");
        }
    }
}
