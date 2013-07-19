package digitalwallet;


import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;
import digitalwallet.mock.HttpArgumentCaptor;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class MockTagTest extends BaseMockTest {

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testListTag() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();
        Tag.getList(10, 1);
        captor.verify(getBaseUrl());
    }

    @org.testng.annotations.Test
    public void testGetPasses() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        String tag = TestHelper.randomTag();
        Tag.getPasses(tag, 10, 1);

        captor.verify(getBaseUrl(tag) + "/passes");
    }

    @org.testng.annotations.Test
    public void testUpdateTag() throws IOException {
        HttpArgumentCaptor<HttpPut> captor = getPutCaptor();

        String tag = TestHelper.randomTag();
        Tag.updatePasses(tag, randomFields());

        captor.verify(getBaseUrl(tag) + "/passes");
    }

    @org.testng.annotations.Test
    public void testDeleteTag() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String tag = TestHelper.randomTag();
        Tag.deleteTag(tag);

        captor.verify(getBaseUrl(tag));
    }

    @org.testng.annotations.Test
    public void testRemoveFromPass() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String tag = TestHelper.randomTag();
        long id = randomId();
        Tag.removeFromPass(tag, id);

        captor.verify(getBaseUrl(tag) + "/pass/" + id);
    }

    @org.testng.annotations.Test
    public void testRemoveFromPassExternal() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String tag = TestHelper.randomTag();
        String externalId = randomExternalId();
        Tag.removeFromPass(tag, externalId);

        captor.verify(getBaseUrl(tag) + "/pass/id/" + externalId);
    }

    @org.testng.annotations.Test
    public void testRemoveFromPasses() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String tag = TestHelper.randomTag();
        Tag.removeFromPasses(tag);

        captor.verify(getBaseUrl(tag) + "/passes");
    }

    @Override
    protected String getBaseUrl() {
        return "/v1/tag";
    }

    @Override
    protected String getBaseUrl(String tag) {
        return getBaseUrl() + "/" + tag;
    }
}
