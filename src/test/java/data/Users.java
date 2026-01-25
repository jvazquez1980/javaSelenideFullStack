package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Users {

    private static Properties secrets;

    static {
        secrets = new Properties();
        try {
            FileInputStream fis = new FileInputStream("secret.properties");
            secrets.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("ERROR: secret.properties file not found! Please create it in the project root.", e);
        }
    }

    // SauceDemo user credentials
    public static class SauceDemoUser {
        public static final String USERNAME = getRequiredProperty("saucedemo.username");
        public static final String PASSWORD = getRequiredProperty("saucedemo.password");
    }

    private static String getRequiredProperty(String key) {
        String value = secrets.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("ERROR: Required property '" + key + "' not found in secret.properties");
        }
        return value;
    }

    // API test data
    public static class ApiTestData {
        public static final String POST_TITLE = "Automated Test Post";
        public static final String POST_BODY = "This is a test post created by automation";
        public static final Integer USER_ID = 1;

        public static final String UPDATED_POST_TITLE = "Updated Automated Test Post";
        public static final String UPDATED_POST_BODY = "This is an updated test post";
    }
}
