package digitalwallet;


import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Template;

import java.util.HashMap;
import java.util.Map;

public class TemplateTest {
    private final String API_KEY = "test";

    /**
     * Create a standard template
     */
    @org.testng.annotations.Test
    public void createTemplate() {
        PassTools.apiKey = API_KEY;

        /* create a template */
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        Long templateId = Template.createTemplate(name, description, type, headers, fields);

        /* get the template and make sure the templates are equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the template */
        Template.delete(templateId);

        /* make sure you can't get the template any more */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
    }

    /**
     * Create a template with an external id
     */
    @org.testng.annotations.Test
    public void createTemplateX() {
        PassTools.apiKey = API_KEY;

        /* create a template with an external id */
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        String externalId = TestHelper.randomString("external-");
        Long templateId = Template.createTemplate(externalId, name, description, type, headers, fields);

        /* get the template by id and make sure it's equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* get the template by the external id and make sure it's equal */
        template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the template */
        Template.deleteX(externalId);

        /* make sure you can't get the template by id */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* make sure you can't get the template by external id */
        try {
            Template.getTemplate(externalId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
    }


    /**
     * Create a template with an external id and a project id
     */
    @org.testng.annotations.Test
    public void createTemplateXP() {
        PassTools.apiKey = API_KEY;
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        String externalId = TestHelper.randomString("external-");
        Long templateId = Template.createTemplate(externalId, name, description, type, headers, fields);

        /* todo list the templates for the project and make sure it's there */

        /* get the template by the id and make sure it's equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* get the template by the external id and make sure it's equal */
        template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the template */
        Template.deleteX(externalId);

        /* make sure you can't get the template by id */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* make sure you can't get the template by external id */
        try {
            Template.getTemplate(externalId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* todo make sure that the template doesn't show up in the list of templates for this project */
    }

    /**
     * Create a template with a project id.
     */
    @org.testng.annotations.Test
    public void createTemplateP() {
        PassTools.apiKey = API_KEY;

        /* todo create a project */
        /* todo create a template with the newly created project id */
        /* todo list the templates for the project and make sure it's there */
    }

    @org.testng.annotations.Test
    public void duplicateTemplate() {
        PassTools.apiKey = API_KEY;
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        Long templateId = Template.createTemplate(name, description, type, headers, fields);

        /* get the template and make sure that they are equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* duplicate the template */
        Long dupId = Template.duplicate(templateId);

        /* get the duplicated template */
        /* verify that the duplicated template equals the original template */
        template = Template.getTemplate(dupId);
        verifyEqual(name, description, type, headers, fields, template);


        /* delete the duplicated template */
        Template.delete(dupId);

        /* verify that the duplicated template is gone */
        try {
            Template.getTemplate(dupId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* delete the original template */
        Template.delete(templateId);

        /* verify that the original template is gone */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
    }

    @org.testng.annotations.Test
    public void duplicateTemplateX() {
        /* create a template with an external id */
        PassTools.apiKey = API_KEY;
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        String externalId = TestHelper.randomString("external-");
        Long templateId = Template.createTemplate(externalId, name, description, type, headers, fields);

        /* get the template and make sure that they are equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* duplicate the template */
        Long dupId = Template.duplicate(externalId);

        /* get the duplicated template */
        template = Template.getTemplate(dupId);
        /* verify that the duplicated template equals the original template */
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the duplicated template */
        Template.delete(dupId);

        /* verify that the duplicated template is gone */
        try {
            Template.getTemplate(dupId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* delete the original template */
        Template.deleteX(externalId);

        /* verify that the original template is gone */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* verify that the original template is gone */
        try {
            Template.getTemplate(externalId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
    }

    @org.testng.annotations.Test
    public void externalId() {

        PassTools.apiKey = API_KEY;
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();

        /* create a template with a external id that contains characters that need to be encoded */
        String externalId = TestHelper.randomString("external&.?; :#\\\"%-");
        Long templateId = Template.createTemplate(externalId, name, description, type, headers, fields);

        /* get the template by its external id  and verify that the templates are equal */
        Template template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* duplicate the template by its external id */
        Long dupId = Template.duplicate(externalId);

        /* get the duplicated template and verify that they are equal */
        template = Template.getTemplate(dupId);
        verifyEqual(name, description, type, headers, fields, template);

        /* update the template */
        name = TestHelper.randomName();
        description = TestHelper.randomDescription();
        Template.updateTemplate(externalId, name, description, headers, fields);

        /* get the template and make sure it has updated values */
        template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* get the template and make sure it has updated values */
        template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the duplicated template and verify it's gone */
        Template.delete(dupId);
        try {
            Template.getTemplate(dupId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* delete template by external id */
        Template.deleteX(externalId);

        /* verify can't get template by template id */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* verify can't get template by external id */
        try {
            Template.getTemplate(externalId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
    }

    @org.testng.annotations.Test
    public void updateTemplate() {
        PassTools.apiKey = API_KEY;
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        Long templateId = Template.createTemplate(name, description, type, headers, fields);

        /* get the template and make sure that they are equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* update the template */
        name = TestHelper.randomName();
        description = TestHelper.randomDescription();
        Template.updateTemplate(templateId, name, description, headers, fields);

        /* get the template and make sure it has updated values */
        template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the original template */
        Template.delete(templateId);

        /* verify that the original template is gone */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
    }

    @org.testng.annotations.Test
    public void updateTemplateX() {
        PassTools.apiKey = API_KEY;
        Map<String, Object> headers = randomHeaders();
        Map<String, Object> fields = randomFields();
        String name = TestHelper.randomName();
        String description = TestHelper.randomDescription();
        String type = randomType();
        String externalId = TestHelper.randomString("external-");
        Long templateId = Template.createTemplate(externalId, name, description, type, headers, fields);

        /* get the template and make sure that they are equal */
        Template template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* get the template and make sure that they are equal */
        template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* update the template */
        name = TestHelper.randomName();
        description = TestHelper.randomDescription();
        Template.updateTemplate(externalId, name, description, headers, fields);

        /* get the template and make sure it has updated values */
        template = Template.getTemplate(templateId);
        verifyEqual(name, description, type, headers, fields, template);

        /* get the template and make sure it has updated values */
        template = Template.getTemplate(externalId);
        verifyEqual(name, description, type, headers, fields, template);

        /* delete the original template */
        Template.deleteX(externalId);

        /* verify that the original template is gone */
        try {
            Template.getTemplate(templateId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }

        /* verify that the original template is gone */
        try {
            Template.getTemplate(externalId);
            assert false;       /* should have thrown a 404 */
        } catch (Exception e) {
        }
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

    private static void verifyEqual(String name, String description, String type, Map<String, Object> headers, Map<String, Object> fields, Template template) {
        assert template != null;
        assert name.equals(template.getName());
        assert description.equals(template.getDescription());

        TemplateTypeEnum e = TemplateTypeEnum.fromJsonKeyName(type);
        assert e != null;
        assert e.getDisplayName().equals(template.getType());

        for (Map.Entry<String, Object> current : fields.entrySet()) {
            Object o = template.getFieldsModel().get(current.getKey());
            assert o != null;
        }
    }
}

