package digitalwallet;


import com.urbanairship.digitalwallet.client.Pass;
import com.urbanairship.digitalwallet.client.PassTools;
import digitalwallet.mock.HttpArgumentCaptor;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class MockPassTest extends BaseMockTest {

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testListPasses() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();
        Pass.listPasses(10, 1);
        captor.verify("/v1/pass");
    }

    @org.testng.annotations.Test
    public void testGetPass() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        long id = randomId();
        Pass.get(id);

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testGetPassExternal() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        String externalId = randomExternalId();
        Pass.getPass(externalId);

        captor.verify(getBaseUrl(externalId));
    }

    @org.testng.annotations.Test
    public void testCreatePass() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        long templateId = randomId();
        Pass.create(templateId, randomFields());

        captor.verify(getBaseUrl(templateId));
    }

    @org.testng.annotations.Test
    public void testCreatePassExternal() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        long templateId = randomId();
        String externalId = randomExternalId();
        Pass.create(templateId, externalId, randomFields());

        captor.verify(getBaseUrl(templateId) + "/id/" + externalId);
    }

    @org.testng.annotations.Test
    public void testCreatePassExternalX() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        String templateExternalId = randomExternalId();
        String externalId = randomExternalId();
        Pass.create(templateExternalId, externalId, randomFields());

        captor.verify(getBaseUrl(templateExternalId) + "/id/" + externalId);
    }

    @org.testng.annotations.Test
    public void testUpdatePass() throws IOException {
        HttpArgumentCaptor<HttpPut> captor = getPutCaptor();

        long id = randomId();
        Pass.update(id, randomFields());

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testUpdatePassExternal() throws IOException {
        HttpArgumentCaptor<HttpPut> captor = getPutCaptor();

        String externalId = randomExternalId();
        Pass.update(externalId, randomFields());

        captor.verify(getBaseUrl(externalId));
    }

    @org.testng.annotations.Test
    public void testDeletePass() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        long id = randomId();
        Pass.delete(id);

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testDeletePassExternal() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String externalId = randomExternalId();
        Pass.deleteX(externalId);

        captor.verify(getBaseUrl(externalId));
    }

    @Override
    protected String getBaseUrl() {
        return "/v1/pass";
    }
}
