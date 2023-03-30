import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class LiveDemo {
    public static void main(String[] args) {
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.rahulshettyacademy.com/seleniumPractise/#/offers");

        // Click on column - sort all values in first column
        driver.findElement(By.cssSelector("tr th:nth-child(1)")).click();

        // Capture all web-elements into list
        List<WebElement> elementsList = driver.findElements(By.cssSelector("tbody tr td:nth-child(1)"));

        // Capture text of all web-elements into new (original) list
        List<String> originalList = elementsList.stream().map(s -> s.getText()).collect(Collectors.toList());

        // Sort on original list of step 3 -> sorted list
        List<String> sortedList = originalList.stream().sorted().collect(Collectors.toList());

        // Compare original list vs sorted list
        Assert.assertTrue(originalList.equals(sortedList));

        List<String> price;

        // Do-while loop to repeat the following code until there is at least one price found
        do {
            List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr td:nth-child(1)"));

            // Filter rows containing vegetable name "Rice" and map them to their prices using getPriceVeggie() method and store all prices in the 'price' List
            price = rows.stream().filter(s -> s.getText().contains("Rice")).map(s -> getPriceVeggie(s)).collect(Collectors.toList());
            price.forEach(a -> System.out.println(a));

            // Click on the "Next" button to go to the next page, if no price is found in the current page
            if (price.size() < 1) {
                driver.findElement(By.cssSelector("[aria-label='Next']")).click();
            }
        } while (price.size() < 1);

    }

    // Method to get price of a vegetable by finding the next sibling of its WebElement
    private static String getPriceVeggie(WebElement s) {
        String priceValue = s.findElement(By.xpath("following-sibling::td[1]")).getText();
        return priceValue;
    }
}
