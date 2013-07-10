package digitalwallet;


import com.urbanairship.digitalwallet.client.Pass;
import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;

import java.util.List;

public class PassTest {

    private final String API_KEY = "test";

    private final static int maxPages = 5;
    private final static int pageSize = 10;

    @org.testng.annotations.Test
    public void listPasses() {
        PassTools.apiKey = API_KEY;
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
        PassTools.apiKey = API_KEY;
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

                boolean found = false;
                for (Tag currentPassTag : passTags) {
                    if (currentPassTag.getTag().equals(tag)) {
                        found = true;
                        break;
                    }
                }
                assert found;

                Tag.removeFromPass(tag, passId);
                found = false;
                for (Tag currentPassTag : passTags) {
                    if (currentPassTag.getTag().equals(tag)) {
                        found = true;
                        break;
                    }
                }
                assert !found;
            }
        }
    }
}
