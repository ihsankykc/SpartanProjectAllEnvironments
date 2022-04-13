package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SpartanDetailPage extends BasePage{
    @FindBy(id = "name")
    public WebElement name;

    @FindBy(id = "gender")
    public WebElement gender;

    @FindBy(id = "phone")
    public WebElement phone;
}
