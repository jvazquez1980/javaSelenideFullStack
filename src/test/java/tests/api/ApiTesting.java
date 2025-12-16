package tests.api;

import api.client.BookingClient;
import api.models.BookingRequest;
import api.models.BookingResponse;
import core.ApiBaseTest;
import data.Users;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("API Testing")
@Feature("Posts Management API")
public class ApiTesting extends ApiBaseTest {

    private BookingClient bookingClient;

    @Test(priority = 1)
    @Story("API endpoints work correctly")
    @Description("Test that verifies API functionality for posts management")
    @Severity(SeverityLevel.CRITICAL)
    public void testApiEndpoints() {
        bookingClient = new BookingClient();

        // Test GET all posts
        Response getAllResponse = bookingClient.getAllPosts();
        bookingClient.verifyStatusCode(getAllResponse, 200);

        var allPosts = bookingClient.parseBookingResponseList(getAllResponse);
        assertFalse(allPosts.isEmpty(), "Posts list should not be empty");
        logger.info("Retrieved {} posts", allPosts.size());

        // Test GET specific post
        Response getPostResponse = bookingClient.getPostById(2);
        bookingClient.verifyStatusCode(getPostResponse, 200);

        BookingResponse post = bookingClient.parseBookingResponse(getPostResponse);
        assertNotNull(post.getId(), "Post ID should not be null");
        assertEquals(post.getId(), Integer.valueOf(2), "Post ID should be 2");

        logger.info("API endpoints test completed successfully");
    }

    @Test(priority = 2)
    @Story("API CRUD operations work correctly")
    @Description("Test that verifies full CRUD operations on API")
    @Severity(SeverityLevel.CRITICAL)
    public void testApiCrudOperations() {
        bookingClient = new BookingClient();

        // Create new post
        BookingRequest createRequest = new BookingRequest(
                Users.ApiTestData.POST_TITLE,
                Users.ApiTestData.POST_BODY,
                Users.ApiTestData.USER_ID
        );

        Response createResponse = bookingClient.createPost(createRequest);
        bookingClient.verifyStatusCode(createResponse, 201);

        BookingResponse createdPost = bookingClient.parseBookingResponse(createResponse);
        assertNotNull(createdPost.getId(), "Created post should have an ID");
        bookingClient.verifyPostData(createdPost, createRequest);

        int createdPostId = createdPost.getId();
        logger.info("Created post with ID: {}", createdPostId);

        // For UPDATE and DELETE operations, use an existing valid ID (1-100)
        // JSONPlaceholder only supports operations on existing posts
        int validPostId = 2; // Use post ID 2 which always exists
        logger.info("Using valid post ID {} for UPDATE/DELETE operations", validPostId);

        // Update the post
        BookingRequest updateRequest = new BookingRequest(
                Users.ApiTestData.UPDATED_POST_TITLE,
                Users.ApiTestData.UPDATED_POST_BODY,
                Users.ApiTestData.USER_ID
        );

        Response updateResponse = bookingClient.updatePost(validPostId, updateRequest);
        bookingClient.verifyStatusCodeIsOneOf(updateResponse, 200, 201);
        logger.info("Updated post with ID: {}", validPostId);

        // Delete the post
        Response deleteResponse = bookingClient.deletePost(validPostId);
        bookingClient.verifyStatusCode(deleteResponse, 200);
        logger.info("Deleted post with ID: {}", validPostId);

        logger.info("API CRUD operations test completed successfully");
    }
}
