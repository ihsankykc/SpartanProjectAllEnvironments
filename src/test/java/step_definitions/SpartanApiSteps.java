package step_definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;

public class SpartanApiSteps {
    String spartanUrl= ConfigurationReader.get("spartan.apiUrl");
    String apiName;
    @When("User sends a request to spartan API using id {int}")
    public void user_sends_a_request_to_spartan_api_using_id(Integer id) {
        Response response=given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id",id)
                .when().get(spartanUrl+"/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        apiName=response.body().path("name");

    }

    @Then("API name should be {string}")
    public void api_name_should_be(String string) {
        Assert.assertEquals(apiName,string);
    }

    @Then("Info of Spartan should be same at all environments")
    public void info_of_spartan_should_be_same_at_all_environments() {
        Assert.assertEquals(apiName,SpartanDBsteps.dbName);
        Assert.assertEquals(apiName,SpartanUiSteps.uiName);

        System.out.println("DataBase = " + SpartanDBsteps.dbName);
        System.out.println("User Interface = " + SpartanUiSteps.uiName);
        System.out.println("API = " + apiName);
    }
}
