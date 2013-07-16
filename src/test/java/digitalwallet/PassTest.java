package digitalwallet;


import com.urbanairship.digitalwallet.client.Pass;
import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;

import java.util.ArrayList;
import java.util.List;

public class PassTest {
    private final static int maxPages = 5;
    private final static int pageSize = 10;

    @org.testng.annotations.Test
    public void listPasses() {
        PassTools.apiKey = TestHelper.getApiKey();
        boolean done = false;
        int page = 0;

        while (!done) {
            List<Pass> passes = Pass.listPasses(pageSize, page);
            if (passes != null && passes.size() > 0 && page < maxPages) {
                page++;
            } else {
                done = true;
            }
        }
    }

    @org.testng.annotations.Test
    public void testPassTags() {
        PassTools.apiKey = TestHelper.getApiKey();
        String tag = TestHelper.randomTag();

        /* step through some passes adding a tag to them */
        List<Pass> passes = Pass.listPasses(3, 0);
        if (passes != null && passes.size() > 0) {

            for (Pass current : passes) {
                Long passId = current.getPassId();

                /* add a tag to the pass */
                List<String> addedTags = Pass.addTag(passId, tag);

                for (String currentTag : addedTags) {
                    /* make sure that the tag was added */
                    assert currentTag.equals(tag);
                }

                /* get the tags for the pass and make sure that the newly created tag is there.*/
                List<Tag> passTags = Pass.getTags(current.getPassId());
                assert passTags != null;
                assert passTags.size() > 0;

                assert contains(passTags, tag);

                Tag.removeFromPass(tag, passId);

                passTags = Pass.getTags(current.getPassId());
                assert !contains(passTags, tag);
            }

            /* delete the tag */
            Tag.deleteTag(tag);
        }
    }

    @org.testng.annotations.Test
    public void testPassDeleteTag() {
        PassTools.apiKey = TestHelper.getApiKey();
        String tag = TestHelper.randomTag();
        List<Long> passIds = new ArrayList<Long>();

        /* step through some passes adding a tag to them */
        List<Pass> passes = Pass.listPasses(3, 0);
        if (passes != null && passes.size() > 0) {

            for (Pass current : passes) {
                Long passId = current.getPassId();
                passIds.add(passId);

                /* add a tag to the pass */
                List<String> addedTags = Pass.addTag(passId, tag);

                for (String currentTag : addedTags) {
                    /* make sure that the tag was added */
                    assert currentTag.equals(tag);
                }

                /* get the tags for the pass and make sure that the newly created tag is there.*/
                List<Tag> passTags = Pass.getTags(passId);
                assert passTags != null;
                assert passTags.size() > 0;

                assert contains(passTags, tag);
            }

            /* delete the tag */
            Tag.deleteTag(tag);

            /* make sure it's not on any of the passes */
            for (Long currentPassId : passIds) {
                List<Tag> passTags = Pass.getTags(currentPassId);
                assert !contains(passTags, tag);
            }
        }
    }

    @org.testng.annotations.Test
    public void testRemoveAllPasses() {
        PassTools.apiKey = TestHelper.getApiKey();
        String tag = TestHelper.randomTag();
        List<Long> passIds = new ArrayList<Long>();

        /* step through some passes adding a tag to them */
        List<Pass> passes = Pass.listPasses(3, 0);
        if (passes != null && passes.size() > 0) {

            for (Pass current : passes) {
                Long passId = current.getPassId();
                passIds.add(passId);

                /* add a tag to the pass */
                List<String> addedTags = Pass.addTag(passId, tag);

                for (String currentTag : addedTags) {
                    /* make sure that the tag was added */
                    assert currentTag.equals(tag);
                }

                /* get the tags for the pass and make sure that the newly created tag is there.*/
                List<Tag> passTags = Pass.getTags(passId);
                assert passTags != null;
                assert passTags.size() > 0;

                assert contains(passTags, tag);
            }

            /* delete the tag */
            Tag.removeFromPasses(tag);

            /* make sure it's not on any of the passes */
            for (Long currentPassId : passIds) {
                List<Tag> passTags = Pass.getTags(currentPassId);
                assert !contains(passTags, tag);
            }

            Tag.deleteTag(tag);
        }
    }

    private boolean contains(List<Tag> tags, String tag) {
        for (Tag current : tags) {
            if (current.getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
