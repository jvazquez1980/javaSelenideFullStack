package data;

public class Users {

    // Admin user credentials
    public static class Admin {
        public static final String USERNAME = "admin";
        public static final String PASSWORD = "password";
    }

    // Test user data for contact forms
    public static class TestUser {
        public static final String NAME = "John Doe";
        public static final String EMAIL = "john.doe@test.com";
        public static final String PHONE = "+1234567890";
        public static final String SUBJECT = "Test Booking Inquiry";
        public static final String MESSAGE = "This is a test message for booking inquiry automation.";
    }

    // Invalid user credentials for negative testing
    public static class InvalidUser {
        public static final String USERNAME = "invaliduser";
        public static final String PASSWORD = "wrongpassword";
    }

    // SauceDemo user credentials
    public static class SauceDemoUser {
        public static final String USERNAME = "visual_user";
        public static final String PASSWORD = "secret_sauce";
    }

    // API test data
    public static class ApiTestData {
        public static final String POST_TITLE = "Automated Test Post";
        public static final String POST_BODY = "This is a test post created by automation";
        public static final Integer USER_ID = 1;

        public static final String UPDATED_POST_TITLE = "Updated Automated Test Post";
        public static final String UPDATED_POST_BODY = "This is an updated test post";
    }

    // Utility methods for generating test data
    public static String generateRandomEmail() {
        long timestamp = System.currentTimeMillis();
        return "test" + timestamp + "@automation.com";
    }

    public static String generateRandomName() {
        String[] firstNames = { "John", "Jane", "Mike", "Sarah", "David", "Emma" };
        String[] lastNames = { "Doe", "Smith", "Johnson", "Brown", "Wilson", "Davis" };

        int firstIndex = (int) (Math.random() * firstNames.length);
        int lastIndex = (int) (Math.random() * lastNames.length);

        return firstNames[firstIndex] + " " + lastNames[lastIndex];
    }

    public static String generateRandomPhone() {
        return "+1" + (long) (Math.random() * 9000000000L + 1000000000L);
    }
}
