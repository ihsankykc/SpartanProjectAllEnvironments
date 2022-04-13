package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Driver;

public class SpartanMainPage extends BasePage{

    public WebElement viewSpartan(String id){
        String path="(//tbody//tr/td)[.='"+id+"']/../td[5]//a[@href]";
         return Driver.get().findElement(By.xpath(path));
    }
}
