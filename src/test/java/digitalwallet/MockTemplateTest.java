package digitalwallet;

import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Template;
import digitalwallet.mock.HttpArgumentCaptor;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class MockTemplateTest extends BaseMockTest {

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testGetTemplate() throws Exception {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        long id = randomId();
        Template.getTemplate(id);

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testGetTemplateExternal() throws Exception {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        String externalId = randomExternalId();
        Template.getTemplate(externalId);

        captor.verify(getBaseUrl(externalId));
    }

    @org.testng.annotations.Test
    public void testCreate() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        Template.createTemplate(TestHelper.randomName(), TestHelper.randomDescription(), randomType(), randomHeaders(), randomFields());

        captor.verify(getBaseUrl());
    }

    @org.testng.annotations.Test
    public void testCreateExternal() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        String externalId = randomExternalId();
        Template.createTemplate(externalId, TestHelper.randomName(), TestHelper.randomDescription(), randomType(), randomHeaders(), randomFields());

        captor.verify(getBaseUrl(externalId));
    }

    @org.testng.annotations.Test
    public void testCreateInProject() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        long id = randomId();
        Template.createTemplate(id, TestHelper.randomName(), TestHelper.randomDescription(), randomType(), randomHeaders(), randomFields());

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testHeaders() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        Template.getMyTemplateHeaders();

        captor.verify(getBaseUrl() + "/headers");
    }

    @org.testng.annotations.Test
    public void testDelete() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        long id = randomId();
        Template.delete(id);

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testDeleteExternal() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String externalId = randomExternalId();
        Template.deleteX(externalId);

        captor.verify(getBaseUrl(externalId));
    }

    @org.testng.annotations.Test
    public void testUpdate() throws IOException {
        HttpArgumentCaptor<HttpPut> captor = getPutCaptor();

        long id = randomId();
        Template.updateTemplate(id, TestHelper.randomName(), TestHelper.randomDescription(), randomHeaders(), randomFields());

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testUpdateExternal() throws IOException {
        HttpArgumentCaptor<HttpPut> captor = getPutCaptor();

        String id = randomExternalId();
        Template.updateTemplate(id, TestHelper.randomName(), TestHelper.randomDescription(), randomHeaders(), randomFields());

        captor.verify(getBaseUrl(id));
    }

    @Override
    protected String getBaseUrl() {
        return "/v1/template";
    }
}
