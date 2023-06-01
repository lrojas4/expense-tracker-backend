package definitions;

import com.example.expensetrackerbackend.ExpenseTrackerBackendApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExpenseTrackerBackendApplication.class)
public class SpringBootCucumberTestDefinitions {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static Response response;


    @Given("A list of expenses are available")
    public void aListOfExpensesAreAvailable() {
        try {
            ResponseEntity<String> response = new RestTemplate()
                    .exchange(BASE_URL + port + "/api/expenses/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> expenses = JsonPath
                    .from(String.valueOf(response
                            .getBody()))
                    .getList("$");
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assert.assertTrue(expenses != null);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @When("I search for one expense by id")
    public void iSearchForOneExpenseById() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/expenses/1/");
        Assert.assertNotNull(response.body());
    }

    @Then("expense is displayed")
    public void expenseIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("I search for expenses by user")
    public void iSearchForExpensesByUser() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/expenses/user/1/");
        Assert.assertNotNull(response.body());
    }
}
