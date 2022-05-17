import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import constants.UrlConstants;

/**
 * To Automate the API
 * @author Aarthi
 */
public class ApiAssignment {
    /**
     * To test the get Api
     */
    @Test
    void testGetStatus() {
        Response response = RestAssured.get(UrlConstants.API_AUTOMATION_URL_PAGE, new Object[0]);
        System.out.println(response.asString());
        System.out.println(response.getBody());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-Type"));
        System.out.println(response.getTime());
        int StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 200);
    }

    @Test
    void testGetName() {
        ((ValidatableResponse)((ValidatableResponse)((ValidatableResponse)((Response)RestAssured.given()
                .get(UrlConstants.API_AUTOMATION_URL_PAGE, new Object[0])).then())
                .statusCode(200)).body("data.id[3]", Matchers.equalTo(10), new Object[0]))
                .body("data.first_name", Matchers.hasItems(new String[]{"Michael", "Byron"}), new Object[0]);
    }

    /**
     * To test the post Api
     */
    @Test
    public void testPost() {
        JSONObject request = new JSONObject();
        request.put("name", "Bryant");
        request.put("job", "BA");
        System.out.println(request);
        System.out.print(request.toJSONString());
        ((ValidatableResponse)((Response)RestAssured.given()
                .header("Content-Type", "application/json", new Object[0])
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()).when()
                .post(UrlConstants.API_AUTOMATION_URL, new Object[0])).then())
                .statusCode(201);
    }
}
