package api.client;

import api.models.BookingRequest;
import api.models.BookingResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingClient {
    private static final Logger logger = LoggerFactory.getLogger(BookingClient.class);
    private static final String POSTS_ENDPOINT = "/posts";
    
    @Step("Get all posts")
    public Response getAllPosts() {
        logger.info("Getting all posts");
        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(POSTS_ENDPOINT)
                .then()
                .extract()
                .response();
    }
    
    @Step("Get post by ID: {postId}")
    public Response getPostById(int postId) {
        logger.info("Getting post by ID: {}", postId);
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", postId)
                .when()
                .get(POSTS_ENDPOINT + "/{id}")
                .then()
                .extract()
                .response();
    }
    
    @Step("Create new post")
    public Response createPost(BookingRequest bookingRequest) {
        logger.info("Creating new post: {}", bookingRequest);
        return given()
                .header("Content-Type", "application/json")
                .body(bookingRequest)
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .extract()
                .response();
    }
    
    @Step("Update post with ID: {postId}")
    public Response updatePost(int postId, BookingRequest bookingRequest) {
        logger.info("Updating post with ID: {} - {}", postId, bookingRequest);
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", postId)
                .body(bookingRequest)
                .when()
                .put(POSTS_ENDPOINT + "/{id}")
                .then()
                .extract()
                .response();
    }
    
    @Step("Delete post with ID: {postId}")
    public Response deletePost(int postId) {
        logger.info("Deleting post with ID: {}", postId);
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", postId)
                .when()
                .delete(POSTS_ENDPOINT + "/{id}")
                .then()
                .extract()
                .response();
    }
    
    @Step("Verify response status code: {expectedStatusCode}")
    public BookingClient verifyStatusCode(Response response, int expectedStatusCode) {
        logger.info("Verifying status code. Expected: {}, Actual: {}", expectedStatusCode, response.getStatusCode());
        response.then().statusCode(expectedStatusCode);
        return this;
    }
    
    @Step("Verify response status code is one of: {expectedStatusCodes}")
    public BookingClient verifyStatusCodeIsOneOf(Response response, int... expectedStatusCodes) {
        int actualStatusCode = response.getStatusCode();
        logger.info("Verifying status code is one of: {}. Actual: {}", java.util.Arrays.toString(expectedStatusCodes), actualStatusCode);
        
        boolean isValid = false;
        for (int expectedCode : expectedStatusCodes) {
            if (actualStatusCode == expectedCode) {
                isValid = true;
                break;
            }
        }
        
        if (!isValid) {
            throw new AssertionError("Expected status code to be one of " + java.util.Arrays.toString(expectedStatusCodes) + 
                                   " but was " + actualStatusCode + ". Response: " + response.asString());
        }
        return this;
    }
    
    @Step("Parse response to BookingResponse object")
    public BookingResponse parseBookingResponse(Response response) {
        logger.info("Parsing response to BookingResponse object");
        return response.as(BookingResponse.class);
    }
    
    @Step("Parse response to list of BookingResponse objects")
    public List<BookingResponse> parseBookingResponseList(Response response) {
        logger.info("Parsing response to list of BookingResponse objects");
        return response.jsonPath().getList("", BookingResponse.class);
    }
    
    @Step("Verify post data matches request")
    public BookingClient verifyPostData(BookingResponse response, BookingRequest request) {
        logger.info("Verifying post data matches request");
        assert response.getTitle().equals(request.getTitle()) : "Title mismatch";
        assert response.getBody().equals(request.getBody()) : "Body mismatch";
        assert response.getUserId().equals(request.getUserId()) : "UserId mismatch";
        return this;
    }
}
