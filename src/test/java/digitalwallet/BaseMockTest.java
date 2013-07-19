package digitalwallet;


import org.apache.http.client.methods.HttpRequestBase;

import java.util.HashMap;
import java.util.Map;

public class BaseMockTest {
    private final static String defaultHostName = "api.passtools.com";

    protected void verify(HttpRequestBase current, String address) {
        assert current.getURI().getPath().equals(address);
        assert current.getURI().getHost().equals(defaultHostName);
    }

    protected Map<String, Object> randomHeaders() {
        return new HashMap<String, Object>();
    }

    protected Map<String, Object> randomFields() {
        return new HashMap<String, Object>();
    }

    protected String randomType() {
        TemplateTypeEnum t = TemplateTypeEnum.getRandomAppleTemplateType();
        return t.getJsonName();
    }

    protected long randomId() {
        return (long)(Math.random() * 1000000);
    }

    protected String randomExternalId() {
        return TestHelper.randomString("External-");
    }
}
