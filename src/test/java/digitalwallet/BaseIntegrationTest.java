package digitalwallet;

import com.urbanairship.digitalwallet.client.PassTools;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

public class BaseIntegrationTest {
    protected final static int maxPages = 5;
    protected final static int pageSize = 10;

    private final static String integrationTestKey = "ua.test.integration";
    private final static String apiKeyKey = "ua.test.digitalWallet.key";
    private final static String apiBaseKey = "ua.test.digitalWallet.apiBase";

    private final static String defaultApiKey = "YOUR_KEY";
    private final static String defaultApiBase = "https://api.passtools.com/v1";

    protected boolean integrationTesting;
    protected String apiKey;
    protected String apiBase;

    protected void initSettings() {
        integrationTesting = false;
        apiKey = defaultApiKey;
        apiBase = defaultApiBase;

        Map<String, String> env = System.getenv();
        if (env.containsKey(integrationTestKey)) {
            String current;
            integrationTesting = Boolean.parseBoolean(env.get(integrationTestKey));

            current = env.get(apiKeyKey);
            if (current != null) {
                apiKey = current;
            }

            current = env.get(apiBaseKey);
            if (current != null) {
                apiBase = current;
            }
        } else {
            integrationTesting = Boolean.parseBoolean(System.getProperty(integrationTestKey, "false"));
            apiKey = System.getProperty("ua.test.digitalWallet.key", defaultApiKey);
            apiBase = System.getProperty("ua.test.digitalWallet.apiBase", defaultApiBase);
        }
    }

    @BeforeSuite
    public void initBase() {
        initSettings();
        if (integrationTesting) {
            PassTools.apiKey = apiKey;
            PassTools.API_BASE = apiBase;
        }
    }
}
