package digitalwallet;

public class TestHelper {
    private final static String API_KEY = "YOUR_KEY";

    public static String randomString(String head) {
        return head + (int)(Math.random() * 10000) + "-" + (int)(Math.random() * 10000) + "-" + (int)(Math.random() * 10000);
    }

    public static String randomDescription() {
        return TestHelper.randomString("description-");
    }

    public static String randomTag() {
        return TestHelper.randomString("tag-");
    }

    public static String randomName() {
        return TestHelper.randomString("name-");
    }

    public Boolean toBool(Object o) {
        Boolean b = false;
        if (o != null) {
            if (o instanceof Boolean) {
                b = (Boolean) o;
            } else if (o instanceof Long) {
                b = (0 != (Long) o);
            } else if (o instanceof Integer) {
                b = (0 != (Integer) o);
            } else if (o instanceof Double) {
                b = (0. != (Double) o);
            } else if (o instanceof String) {
                String str = (String) o;
                b = str.equalsIgnoreCase("true") || str.equals("1");
            }
        }
        return b;
    }

    public Long toLong(Object o) {
        Long l = null;

        if (o != null) {
            if (o instanceof Long) {
                l = (Long) o;
            } else {
                try {
                    l = Long.valueOf(o.toString());
                } catch (NumberFormatException e) {
                    l = null;
                }
            }
        }

        return l;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
