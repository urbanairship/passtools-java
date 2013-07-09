package digitalwallet;

import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Project;

import java.util.List;

public class ProjectTest {
    private final String API_KEY = "test";

    private final static int maxPages = 5;
    private final static int pageSize = 10;

    @org.testng.annotations.Test
    public void testGetProjectList() {
        PassTools.apiKey = API_KEY;
        boolean done = false;
        int page = 0;
        while (!done) {
            List<Project> projects = Project.getProjects(pageSize, page);
            if (projects != null && projects.size() > 0 && page < maxPages) {
                page++;
                for (Project project : projects) {
                    Project p2 = Project.getProject(project.getId());
                    assertEquals(project, p2);
                }
            } else {
                done = true;
            }
        }
    }

    @org.testng.annotations.Test
    public void testCreateProject() {
        PassTools.apiKey = API_KEY;
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String projectType = "coupon";

        Project createdProject = Project.createProject(name, description, projectType);
        assert createdProject != null;
        assertEquals(createdProject.getId(), name, description, projectType, createdProject);

        Long projectId = createdProject.getId();
        Project project = Project.getProject(createdProject.getId());
        assertEquals(projectId, name, description, projectType, project);

        Project.deleteProject(projectId);

        try {
            Project.getProject(projectId);
            assert false; /* should have thrown a 404 */
        } catch (Exception e) {

        }
    }

    @org.testng.annotations.Test
    public void testCreateProjectX() {
        PassTools.apiKey = API_KEY;
        String externalId = TestHelper.randomString("external-");
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String projectType = "coupon";

        Project createdProject = Project.createProject(externalId, name, description, projectType);
        assert createdProject != null;
        Long projectId = createdProject.getId();
        assertEquals(projectId, name, description, projectType, createdProject);

        Project project = Project.getProject(createdProject.getId());
        assertEquals(projectId, name, description, projectType, project);

        project = Project.getProject(externalId);
        assertEquals(projectId, name, description, projectType, project);

        Project.deleteProject(externalId);

        try {
            Project.getProject(externalId);
            assert false; /* should have thrown a 404 */
        } catch (Exception e) {

        }

        try {
            Project.getProject(projectId);
            assert false; /* should have thrown a 404 */
        } catch (Exception e) {

        }
    }

    @org.testng.annotations.Test
    public void testUpdateProject() {
        PassTools.apiKey = API_KEY;
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String projectType = "coupon";

        Project createdProject = Project.createProject(name, description, projectType);
        assert createdProject != null;
        assertEquals(createdProject.getId(), name, description, projectType, createdProject);

        Long projectId = createdProject.getId();
        Project project = Project.getProject(createdProject.getId());
        assertEquals(projectId, name, description, projectType, project);

        name = TestHelper.randomName();
        description = TestHelper.randomDescription();
        Project.updateProject(projectId, name, description);

        project = Project.getProject(createdProject.getId());
        assertEquals(projectId, name, description, projectType, project);

        Project.deleteProject(projectId);

        try {
            Project.getProject(projectId);
            assert false; /* should have thrown a 404 */
        } catch (Exception e) {

        }
    }

    @org.testng.annotations.Test
    public void testUpdateProjectX() {
        PassTools.apiKey = API_KEY;
        String externalId = TestHelper.randomString("external-");
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String projectType = "coupon";

        Project createdProject = Project.createProject(externalId, name, description, projectType);
        assert createdProject != null;
        Long projectId = createdProject.getId();
        assertEquals(projectId, name, description, projectType, createdProject);

        Project project = Project.getProject(createdProject.getId());
        assertEquals(projectId, name, description, projectType, project);

        project = Project.getProject(externalId);
        assertEquals(projectId, name, description, projectType, project);

        name = TestHelper.randomName();
        description = TestHelper.randomDescription();
        Project.updateProject(externalId, name, description);

        project = Project.getProject(externalId);
        assertEquals(projectId, name, description, projectType, project);

        Project.deleteProject(externalId);

        try {
            Project.getProject(externalId);
            assert false; /* should have thrown a 404 */
        } catch (Exception e) {

        }

        try {
            Project.getProject(projectId);
            assert false; /* should have thrown a 404 */
        } catch (Exception e) {

        }
    }

    private void assertEquals(Project p1, Project p2) {
        assert p1 != null;
        assert p2 != null;
        assert p1.getDescription().equals(p2.getDescription());
        assert p1.getId().equals(p2.getId());
        assert p1.getName().equals(p2.getName());
        assert p1.getProjectType().equals(p2.getProjectType());
    }

    private void assertEquals(Long projectId, String name, String description, String projectType, Project project) {
        assert description.equals(project.getDescription());
        assert name.equals(project.getName());
        assert projectType.equals(project.getProjectType());
        assert projectId.equals(project.getId());
    }
}
