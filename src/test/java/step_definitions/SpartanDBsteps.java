package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utilities.DBUtils;
import utilities.Driver;

import java.util.List;
import java.util.Map;

public class SpartanDBsteps {

    List<Map<String, Object>> spartanMap;
    static String dbName;
    @When("User gets info of a certain spartan from DB with ID {int}")
    public void user_gets_info_of_a_certain_spartan_from_db_with_id(Integer id) {
        String query="select * from spartans where spartan_id="+id;
        spartanMap = DBUtils.getQueryResultMap(query);
        dbName= (String) spartanMap.get(0).get("NAME");
    }
    @Then("Spartan name should be {string}")
    public void spartan_name_should_be(String expectedName) {
        Assert.assertEquals(dbName,expectedName);
    }

}
