package digitalwallet;

public class TestHelper {
    public static String randomString(String head) {
        return head + (int)(Math.random() * 10000) + "-" + (int)(Math.random() * 10000) + "-" + (int)(Math.random() * 10000);
    }

    public static String randomDescription() {
        return TestHelper.randomString("description-");
    }

    public static String randomName() {
        return TestHelper.randomString("name-");
    }
}
