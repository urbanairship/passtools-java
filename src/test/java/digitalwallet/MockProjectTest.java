package digitalwallet;


import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Project;
import digitalwallet.mock.HttpArgumentCaptor;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class MockProjectTest extends BaseMockTest {

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testListProjects() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        Project.getProjects(10, 1);

        captor.verify(getBaseUrl());
    }

    @org.testng.annotations.Test
    public void testGetProject() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        long id = randomId();
        Project.getProject(id);

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testGetProjectExternal() throws IOException {
        HttpArgumentCaptor<HttpGet> captor = getGetCaptor();

        String externalId = randomExternalId();
        Project.getProject(externalId);

        captor.verify(getBaseUrl(externalId));
    }

    @org.testng.annotations.Test
    public void testCreateProject() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        Project.createProject(TestHelper.randomName(), TestHelper.randomDescription(), "coupon");

        captor.verify(getBaseUrl());
    }

    @org.testng.annotations.Test
    public void testCreateProjectExternal() throws IOException {
        HttpArgumentCaptor<HttpPost> captor = getPostCaptor();

        String external = randomExternalId();
        Project.createProject(external, TestHelper.randomName(), TestHelper.randomDescription(), "coupon");

        captor.verify(getBaseUrl(external));
    }

    @org.testng.annotations.Test
    public void testUpdateProject() throws IOException {
        HttpArgumentCaptor<HttpPut> captor = getPutCaptor();

        long id = randomId();
        Project.updateProject(id, TestHelper.randomName(), TestHelper.randomDescription());

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testDeleteProject() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        long id = randomId();
        Project.deleteProject(id);

        captor.verify(getBaseUrl(id));
    }

    @org.testng.annotations.Test
    public void testDeleteProjectExternal() throws IOException {
        HttpArgumentCaptor<HttpDelete> captor = getDeleteCaptor();

        String externalId = randomExternalId();
        Project.deleteProject(externalId);

        captor.verify(getBaseUrl(externalId));
    }

    @Override
    protected String getBaseUrl() {
        return "/v1/project";
    }
}
