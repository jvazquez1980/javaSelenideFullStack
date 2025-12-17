package tests.api;

import api.client.BookingClient;
import core.ApiBaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("API Testing")
@Feature("Schema Validation")
public class ApiSchemaTest extends ApiBaseTest {

    private BookingClient bookingClient;

    @Test
    @Story("API schema is correct")
    @Description("Validate JSON schema for GET /users list")
    @Severity(SeverityLevel.NORMAL)
    public void testUsersListSchema() {
        bookingClient = new BookingClient();

        Response getAllResponse = bookingClient.getAllUsers();
        bookingClient.verifyStatusCode(getAllResponse, 200);

        getAllResponse
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/users-list-schema.json"));
    }
}
