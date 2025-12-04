package tests;

import api.client.BookingClient;
import api.models.BookingRequest;
import api.models.BookingResponse;
import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import steps.BookingSteps;
import steps.LoginSteps;

import static org.testng.Assert.*;

@Epic("End-to-End Automation Testing")
@Feature("Hotel Booking System")
public class EndToEndTest extends BaseTest {
    
    private BookingSteps bookingSteps;
    private LoginSteps loginSteps;
    private BookingClient bookingClient;
    
    @Test(priority = 1)
    @Story("User can browse and interact with booking system")
    @Description("Test that verifies user can navigate through the booking system and submit contact form")
    @Severity(SeverityLevel.CRITICAL)
    public void testBookingSystemNavigation() {
        bookingSteps = new BookingSteps();
        
        // Navigate to the booking website
        navigateToUrl(UI_BASE_URL);
        
        // Perform booking flow with test data
        bookingSteps.completeBookingFlow(
                Users.TestUser.NAME,
                Users.TestUser.EMAIL,
                Users.TestUser.PHONE,
                Users.TestUser.SUBJECT,
                Users.TestUser.MESSAGE
        );
        
        logger.info("Booking system navigation test completed successfully");
    }
    
    @Test(priority = 2)
    @Story("Admin can login to the system")
    @Description("Test that verifies admin login functionality")
    @Severity(SeverityLevel.HIGH)
    public void testAdminLogin() {
        loginSteps = new LoginSteps();
        
        // Navigate to the booking website
        navigateToUrl(UI_BASE_URL);
        
        // Perform admin login
        loginSteps.performAdminLogin(Users.Admin.USERNAME, Users.Admin.PASSWORD)
                .verifyLoginSuccess();
        
        // Perform logout
        loginSteps.performLogout();
        
        logger.info("Admin login test completed successfully");
    }
    
    @Test(priority = 3)
    @Story("Invalid login attempts are handled correctly")
    @Description("Test that verifies system handles invalid login attempts properly")
    @Severity(SeverityLevel.MEDIUM)
    public void testInvalidLogin() {
        loginSteps = new LoginSteps();
        
        // Navigate to the booking website
        navigateToUrl(UI_BASE_URL);
        
        // Attempt login with invalid credentials
        loginSteps.attemptInvalidLogin(Users.InvalidUser.USERNAME, Users.InvalidUser.PASSWORD);
        
        logger.info("Invalid login test completed successfully");
    }
    
    @Test(priority = 4)
    @Story("API endpoints work correctly")
    @Description("Test that verifies API functionality for posts management")
    @Severity(SeverityLevel.HIGH)
    public void testApiEndpoints() {
        bookingClient = new BookingClient();
        
        // Test GET all posts
        Response getAllResponse = bookingClient.getAllPosts();
        bookingClient.verifyStatusCode(getAllResponse, 200);
        
        var allPosts = bookingClient.parseBookingResponseList(getAllResponse);
        assertFalse(allPosts.isEmpty(), "Posts list should not be empty");
        logger.info("Retrieved {} posts", allPosts.size());
        
        // Test GET specific post
        Response getPostResponse = bookingClient.getPostById(1);
        bookingClient.verifyStatusCode(getPostResponse, 200);
        
        BookingResponse post = bookingClient.parseBookingResponse(getPostResponse);
        assertNotNull(post.getId(), "Post ID should not be null");
        assertEquals(post.getId(), Integer.valueOf(1), "Post ID should be 1");
        
        logger.info("API endpoints test completed successfully");
    }
    
    @Test(priority = 5)
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
        
        int postId = createdPost.getId();
        logger.info("Created post with ID: {}", postId);
        
        // Update the post
        BookingRequest updateRequest = new BookingRequest(
                Users.ApiTestData.UPDATED_POST_TITLE,
                Users.ApiTestData.UPDATED_POST_BODY,
                Users.ApiTestData.USER_ID
        );
        
        Response updateResponse = bookingClient.updatePost(postId, updateRequest);
        bookingClient.verifyStatusCode(updateResponse, 200);
        
        BookingResponse updatedPost = bookingClient.parseBookingResponse(updateResponse);
        bookingClient.verifyPostData(updatedPost, updateRequest);
        logger.info("Updated post with ID: {}", postId);
        
        // Delete the post
        Response deleteResponse = bookingClient.deletePost(postId);
        bookingClient.verifyStatusCode(deleteResponse, 200);
        logger.info("Deleted post with ID: {}", postId);
        
        logger.info("API CRUD operations test completed successfully");
    }
    
    @Test(priority = 6)
    @Story("Complete End-to-End workflow")
    @Description("Test that combines UI and API operations in a complete workflow")
    @Severity(SeverityLevel.BLOCKER)
    public void testCompleteEndToEndWorkflow() {
        bookingSteps = new BookingSteps();
        loginSteps = new LoginSteps();
        bookingClient = new BookingClient();
        
        // Step 1: API - Create a booking post
        BookingRequest apiRequest = new BookingRequest(
                "Hotel Booking Request",
                "Booking request created via API for E2E test",
                Users.ApiTestData.USER_ID
        );
        
        Response createResponse = bookingClient.createPost(apiRequest);
        bookingClient.verifyStatusCode(createResponse, 201);
        BookingResponse createdPost = bookingClient.parseBookingResponse(createResponse);
        logger.info("API: Created booking post with ID: {}", createdPost.getId());
        
        // Step 2: UI - Navigate to booking system
        navigateToUrl(UI_BASE_URL);
        bookingSteps.navigateToBooking();
        
        // Step 3: UI - Submit contact form
        bookingSteps.submitContactInquiry(
                Users.generateRandomName(),
                Users.generateRandomEmail(),
                Users.generateRandomPhone(),
                "E2E Test Booking",
                "This booking was created during E2E testing"
        );
        
        // Step 4: UI - Admin login
        loginSteps.performAdminLogin(Users.Admin.USERNAME, Users.Admin.PASSWORD)
                .verifyLoginSuccess();
        
        // Step 5: API - Verify the post still exists
        Response getResponse = bookingClient.getPostById(createdPost.getId());
        bookingClient.verifyStatusCode(getResponse, 200);
        
        // Step 6: UI - Logout
        loginSteps.performLogout();
        
        // Step 7: API - Clean up - delete the post
        Response deleteResponse = bookingClient.deletePost(createdPost.getId());
        bookingClient.verifyStatusCode(deleteResponse, 200);
        
        logger.info("Complete End-to-End workflow test completed successfully");
    }
}
