package step_definitions;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pages.pojo.Spartan;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class SpartanFlowStepDefs {

    Response mockApiResponse;
    Response postResponse;
    Response spartanApiResponse;

    Spartan spartan;
    @Given("User sends a request to Mock API for a mock Spartan Data")
    public void user_sends_a_request_to_mock_api_for_a_mock_spartan_data() {
             mockApiResponse =given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().queryParam("key","3f9a59e0")
                .when().get(ConfigurationReader.get("mock.apiUrl"));

        assertEquals(mockApiResponse.statusCode(),200);
        assertEquals(mockApiResponse.contentType(),"application/json");

        mockApiResponse.prettyPrint();

    }
    @When("User uses Mock Data to create a Spartan")
    public void user_uses_mock_data_to_create_a_spartan() {
        spartan=new Spartan();
        spartan.setName(mockApiResponse.path("name"));
        spartan.setGender(mockApiResponse.path("gender"));
        Long phone=Long.valueOf(mockApiResponse.path("phone"));
        spartan.setPhone(phone);

        postResponse=given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartan).log().all()
                .when().post(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans");
        postResponse.prettyPrint();
        assertEquals(postResponse.statusCode(),201);

    }

    @When("User sends a request to Spartan API with id {int}")
    public void user_sends_a_request_to_spartan_api_with_id(int id) {

      if (id==0) {
         id=postResponse.getBody().path("data.id");

      }
        spartanApiResponse = given().accept(ContentType.JSON)
                .and().pathParams("id",id)
                .when().get(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans/{id}");


    }

    @Then("Created Spartan has same information with Post Request")
    public void created_spartan_has_same_information_with_post_request() {
        String actualName=spartanApiResponse.getBody().path("data.name");
        String expectedName=postResponse.path("name");

        String actualGender=spartanApiResponse.getBody().path("data.gender");
        String expectedGender=postResponse.path("gender");

        String actualPhone=spartanApiResponse.getBody().path("data.phone");
        String expectedPhone=postResponse.path("phone");

        assertEquals(expectedName,actualName);
        assertEquals(expectedGender,actualGender);
        assertEquals(expectedPhone,actualPhone);

        System.out.println("created Spartan");
        spartanApiResponse.prettyPrint();

    }
    @Then("User Updates all the fields of created Spartan")
    public void user_updates_all_the_fields_of_created_spartan() {
        spartan.setName("AgentSmith");
        spartan.setGender("Male");
        spartan.setPhone(5551234566L);

        int id = postResponse.path("data.id");
        Response putResponse = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)   // Hey API I am sending you JSON body
                .and().pathParam("id",id)
                .and()
                .body(spartan)
                .when()
                .put(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans/{id}");

        assertEquals(204,putResponse.statusCode());

        spartanApiResponse= given().accept(ContentType.JSON)
                .and().pathParams("id",id)
                .when().get(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans/{id}");

        System.out.println("updated Spartan");
        spartanApiResponse.prettyPrint();

    }

    @Then("User deletes spartan {int}")
    public void user_deletes_spartan(int id) {
        if(id==0){
            id = postResponse.path("data.id");
        }

        given().pathParam("id",id)
                .when()
                .delete(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans/{id}")
                .then()
                .statusCode(204);

        Response deletedSpartan=given().accept(ContentType.JSON)
                .and()
                .pathParam("id",id)
                .and()
                .get(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans/{id}");

        assertEquals(deletedSpartan.statusCode(),404);

        spartanApiResponse= given().accept(ContentType.JSON)
                .and().pathParams("id",id)
                .when().get(ConfigurationReader.get("spartan.apiUrl")+"/api/spartans/{id}");

        System.out.println("deleted Spartan");
        spartanApiResponse.prettyPrint();
    }
}
