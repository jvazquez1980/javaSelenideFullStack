package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public static String toJson(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            logger.debug("Object converted to JSON: {}", json);
            return json;
        } catch (JsonProcessingException e) {
            logger.error("Error converting object to JSON", e);
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            T object = objectMapper.readValue(json, clazz);
            logger.debug("JSON converted to object: {}", object);
            return object;
        } catch (IOException e) {
            logger.error("Error converting JSON to object", e);
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }
    
    public static String prettyPrint(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error pretty printing object", e);
            return object.toString();
        }
    }
    
    public static boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (IOException e) {
            logger.warn("Invalid JSON: {}", json);
            return false;
        }
    }
    
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
