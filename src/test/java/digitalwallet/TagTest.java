package digitalwallet;

import com.google.common.base.Preconditions;
import com.urbanairship.digitalwallet.client.Pass;
import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;
import com.urbanairship.digitalwallet.client.Template;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TagTest {
    private final String API_KEY = "test";

    private final static int maxPages = 5;
    private final static int pageSize = 10;

    private Long templateId = null;
    private boolean initDone = false;
    private String randomTag;
    private String randomExternalId;
    private Pass pass = null;

    private String passExternalId;

    @BeforeClass
    public void init() {
        PassTools.apiKey = API_KEY;
        randomTag = genRandomString();
        randomExternalId = genRandomString();
        passExternalId = genRandomString();
        templateId = TestData.createTemplate(randomExternalId, true);
        if (templateId == 0) {
            return;
        }
        Map fields = TestData.getCreatePassFields();
        pass = Pass.create(templateId, passExternalId, fields);
        if (pass == null || pass.getPassId() == null) {
            return;
        }
        List<String> tags = Pass.addTag(pass.getPassId(), randomTag);
        initDone = true;
    }

    @AfterClass
    public void cleanup() {
        try {
            if (templateId != null) {
                Template.delete(templateId);
            }
            if (pass.getPassId() != null) {
                Pass.delete(pass.getPassId());
            }
            Tag.deleteTag(randomTag);
        } catch (Exception e) {

        }
    }

    private String genRandomTag() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char c = (char) r.nextInt(128);
            sb.append(c);
        }
        return sb.toString();
    }

    private String genRandomString() {
        return RandomStringUtils.randomAlphanumeric(8);
    }


    @org.testng.annotations.Test(priority = 1)
    public void listTagsTest() {
        Preconditions.checkArgument(initDone);
        boolean done = false;
        int page = 0;
        while (!done) {
            List<Tag> tags = Tag.getList(pageSize, page);
            if (tags != null && tags.size() > 0 && page < maxPages) {
                page++;

                for (Tag current : tags) {
                    Tag.getPasses(current.getTag(), 20, 1);
                }
            } else {
                done = true;
            }
        }
    }

    @Test(priority = 2)
    public void getPassesTest() {
        Preconditions.checkArgument(initDone);
        List<Pass> passes = Tag.getPasses(randomTag);
        if (passes == null || passes.size() == 0) {
            assert false;
        }
        Boolean found = false;
        for (Pass p : passes) {
            if (p.getPassId().equals(pass.getPassId())) {
                found = true;
                break;
            }
        }

        assert found.equals(true);

    }

    @Test(priority = 3)
    public void updatePassesTest() {
        Preconditions.checkArgument(initDone);
        Map<String, Object> updateFields = TestData.getUpdatePassFields();
        Long ticketId = Tag.updatePasses(randomTag, updateFields);
        if (ticketId == null) {
            assert false;
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            assert false;
        }
        Pass p = Pass.get(pass.getPassId());
        Map fields = p.getFields();
        if (fields == null) {
            assert false;
        }
    }


    @Test(priority = 99)
    public void deleteTagTest() {
        Preconditions.checkArgument(initDone);
        boolean done = Tag.deleteTag(randomTag);
        assert done;
    }


    @Test(priority = 100)
    public void removeFromPassesTest() {
        Preconditions.checkArgument(initDone);
        Map fields = TestData.getCreatePassFields();
        Pass pass1 = Pass.create(templateId, fields);
        if (pass1 == null || pass1.getPassId() == null) {
            return;
        }
        String lRandomTag = genRandomString();
        List<String> tags = Pass.addTag(pass1.getPassId(), lRandomTag);

        Tag.removeFromPasses(lRandomTag);
        List<Pass> passes = Tag.getPasses(lRandomTag);
        if (passes == null || passes.size() == 0) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test(priority = 97)
    public void removeFromPassTest() {
        Preconditions.checkArgument(initDone);
        boolean removed = Tag.removeFromPass(randomTag, pass.getPassId());
        assert removed;
    }


    @Test(priority = 98)
    public void removeFromPassXTest() {
        Preconditions.checkArgument(initDone);
        Map fields = TestData.getCreatePassFields();
        String lExternalId = genRandomString();
        Pass pass1 = Pass.create(templateId, lExternalId, fields);
        if (pass1 == null || pass1.getPassId() == null) {
            return;
        }
        String lRandomTag = genRandomString();
        List<String> tags = Pass.addTag(pass1.getPassId(), lRandomTag);
        boolean removed = Tag.removeFromPass(lRandomTag, lExternalId);
        assert removed;
    }


    private void assertEquals(Tag t1, Tag t2) {
        assert t1 != null;
        assert t2 != null;
        assert t1.getId().equals(t2.getId());
        assert t1.getTag().equals(t2.getTag());
    }
}
