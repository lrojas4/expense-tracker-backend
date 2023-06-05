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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExpenseTrackerBackendApplication.class)
public class SpringBootCucumberTestDefinitions {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static Response response;

    /**
     * Generates a jwt key every time a user logs in
     * @return jwt key
     * @throws JSONException if arguments cannot be converted to JSON
     */
    public String getSecurityKey() throws JSONException {
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "mail1@gmail.com");
        requestBody.put("password", "psw1");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/login/");
        return response.jsonPath().getString("message");
    }

    @Given("That an user is able to register")
    public void thatAnUserIsAbleToRegister() {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", "email1@mail.com");
            requestBody.put("password", "psw1");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/auth/register/", HttpMethod.POST, request, String.class);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @When("I login to my account")
    public void iLoginToMyAccount() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "email1@mail.com");
        requestBody.put("password", "psw1");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/login/");
    }

    @Then("JWT key is returned")
    public void jwtKeyIsReturned() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

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

    @Then("The expense is displayed")
    public void expenseIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("I search for expenses by user")
    public void iSearchForExpensesByUser() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/expenses/");
        Assert.assertNotNull(response.body());
    }

    @Then("A list of expenses is displayed")
    public void aListOfExpensesIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @When("I add an expense to my expense list")
    public void iAddAnExpenseToMyExpenseList() throws JSONException  {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        JSONObject requestBody = new JSONObject();
        requestBody.put("date", LocalDate.of(2023, 06, 01));
        requestBody.put("amount", 600.00);
        requestBody.put("description", "travel");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/expenses/");
    }

    @Then("the expense is added")
    public void theExpenseIsAdded() {
        Assert.assertEquals(201, response.getStatusCode());
    }

    @When("I update an expense from my list of expenses")
    public void iUpdateAnExpenseFromMyListOfExpenses() throws Exception {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        JSONObject requestBody = new JSONObject();
        requestBody.put("date", LocalDate.of(2023, 06, 02));
        requestBody.put("amount", 50.00);
        requestBody.put("description", "entertainment");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/expenses/1/");
    }

    @Then("The expense is updated")
    public void theExpenseIsUpdated() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @When("I delete an expense from expense list")
    public void iDeleteAnExpenseFromExpenseList() throws Exception {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        request.header("Content-Type", "application/json");
        response = request.delete(BASE_URL + port + "/api/expenses/1/");
    }


    @Then("The expense is deleted")
    public void theExpenseIsDeleted() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @Given("A list of incomes are available")
    public void aListOfIncomesAreAvailable() throws JSONException {
        try {
            String jwtKey = getSecurityKey();
            JSONObject requestBody = new JSONObject();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
            ResponseEntity<String> response = new RestTemplate()
                    .exchange(BASE_URL + port + "/api/incomes/", HttpMethod.GET, request, String.class);
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

    @When("I search for one income by id")
    public void iSearchForOneIncomeById() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/incomes/1/");
        Assert.assertNotNull(response.body());
    }

    @Then("The income is displayed")
    public void theIncomeIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("I search for incomes by user")
    public void iSearchForIncomesByUser() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/incomes/");
        Assert.assertNotNull(response.body());
    }

    @Then("A list of incomes is displayed")
    public void aListOfIncomesIsDisplayed() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @When("I add an income to my income list")
    public void iAddAnIncomeToMyIncomeList() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        JSONObject requestBody = new JSONObject();
        requestBody.put("income_amount", 8000.00);
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/incomes/");
    }


    @Then("The income is added")
    public void theIncomeIsAdded() {
        Assert.assertEquals(201, response.getStatusCode());
    }

    @When("I update an income from my list of incomes")
    public void iUpdateAnIncomeFromMyListOfIncomes() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        JSONObject requestBody = new JSONObject();
        requestBody.put("income_amount", 4000.00);
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/incomes/1/");
    }

    @Then("The income is updated")
    public void theIncomeIsUpdated() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }


    @When("I search for expenses by category id")
    public void iSearchForExpensesByCategoryId() throws JSONException{
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        request.header("Content-Type", "application/json");
        response = request.get(BASE_URL + port + "/api/categories/1/expenses/");

    }

    @Then("A list of expenses by category is displayed")
    public void aListOfExpensesByCategoryIsDisplayed() {
        Assert.assertNotNull(response.body());
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("I delete an income from income list")
    public void iDeleteAnIncomeFromIncomeList() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + jwtKey);
        request.header("Content-Type", "application/json");
        response = request.delete(BASE_URL + port + "/api/incomes/1/");
    }

    @Then("The income is deleted")
    public void theIncomeIsDeleted() {
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }
}
