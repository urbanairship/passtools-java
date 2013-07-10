package digitalwallet;

import com.urbanairship.digitalwallet.client.PassTools;
import com.urbanairship.digitalwallet.client.Tag;

import java.util.List;

public class TagTest {
    private final String API_KEY = "test";

    private final static int maxPages = 5;
    private final static int pageSize = 10;

    @org.testng.annotations.Test
    public void listTags() {
        PassTools.apiKey = API_KEY;
        boolean done = false;
        int page = 0;
        while (!done) {
            List<Tag> tags = Tag.getList(pageSize, page);
            if (tags != null && tags.size() > 0 && page < maxPages) {
                page++;

                for (Tag current : tags) {
                    Tag.getPasses(current.getTag());
                }
            } else {
                done = true;
            }
        }
    }

    private void assertEquals(Tag t1, Tag t2) {
        assert t1 != null;
        assert t2 != null;
        assert t1.getId().equals(t2.getId());
        assert t1.getTag().equals(t2.getTag());
    }
}
