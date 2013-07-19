package digitalwallet;


import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Project;
import digitalwallet.mock.MockHttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MockProjectTest extends BaseMockTest {
    @Mock
    private HttpClient httpClient;

    @Mock
    private Project project;

    @BeforeSuite
    private void setup() {
        MockitoAnnotations.initMocks(this);
        PassTools.apiKey = TestHelper.getApiKey();
        PassTools.client = httpClient;
    }

    @org.testng.annotations.Test
    public void testListProjects() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Project.getProjects(10, 1);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/project");
        }
    }

    @org.testng.annotations.Test
    public void testGetProject() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Project.getProject(id);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/project/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testGetProjectExternal() throws IOException {
        ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String externalId = randomExternalId();
        Project.getProject(externalId);

        for (HttpGet current : captor.getAllValues()) {
            verify(current, "/v1/project/id/" + externalId);
        }
    }

    @org.testng.annotations.Test
    public void testCreateProject() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        Project.createProject(TestHelper.randomName(), TestHelper.randomDescription(), "coupon");

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/project");
        }
    }

    @org.testng.annotations.Test
    public void testCreateProjectExternal() throws IOException {
        ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String external = randomExternalId();
        Project.createProject(external, TestHelper.randomName(), TestHelper.randomDescription(), "coupon");

        for (HttpPost current : captor.getAllValues()) {
            verify(current, "/v1/project/id/" + external);
        }
    }

    @org.testng.annotations.Test
    public void testUpdateProject() throws IOException {
        ArgumentCaptor<HttpPut> captor = ArgumentCaptor.forClass(HttpPut.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Project.updateProject(id, TestHelper.randomName(), TestHelper.randomDescription());

        for (HttpPut current : captor.getAllValues()) {
            verify(current, "/v1/project/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testDeleteProject() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        long id = randomId();
        Project.deleteProject(id);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/project/" + id);
        }
    }

    @org.testng.annotations.Test
    public void testDeleteProjectExternal() throws IOException {
        ArgumentCaptor<HttpDelete> captor = ArgumentCaptor.forClass(HttpDelete.class);
        when(httpClient.execute(captor.capture())).thenReturn(new MockHttpResponse());

        String externalId = randomExternalId();
        Project.deleteProject(externalId);

        for (HttpDelete current : captor.getAllValues()) {
            verify(current, "/v1/project/id/" + externalId);
        }
    }
}
