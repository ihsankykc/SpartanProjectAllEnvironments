package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.SpartanDetailPage;
import pages.SpartanMainPage;
import utilities.ConfigurationReader;
import utilities.Driver;

public class SpartanUiSteps {
    static String uiName;
    @Given("Commander is at the main page")
    public void commander_is_at_the_main_page() throws InterruptedException {
        Driver.get().get(ConfigurationReader.get("spartan.webUrl"));
        Thread.sleep(1000);

    }
    @When("Commander clicks on view button of the spartan with the ID number {string}")
    public void commander_clicks_on_view_button_of_the_spartan_with_the_id_number(String id) throws InterruptedException {
        Thread.sleep(1000);

        SpartanMainPage spartanMainPage=new SpartanMainPage();
        spartanMainPage.viewSpartan(id).click();

        Thread.sleep(1000);

        SpartanDetailPage spartanDetailPage=new SpartanDetailPage();
        uiName=spartanDetailPage.name.getAttribute("value");

    }

    @Then("Spartan names from UI and DB should match")
    public void spartan_names_from_ui_and_db_should_match() {
        Assert.assertEquals(uiName,SpartanDBsteps.dbName);
    }

    @Then("Verifies the name of the spartan is {string}")
    public void verifies_the_name_of_the_spartan_is(String string) {
        Assert.assertEquals(uiName,string);
    }

}
