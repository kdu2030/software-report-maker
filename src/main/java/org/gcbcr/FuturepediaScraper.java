package org.gcbcr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class FuturepediaScraper {
    private final String FUTUREPEDIA_URL = "https://www.futurepedia.io/";

    private final WebDriver driver;

    public FuturepediaScraper(String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-disable-extensions");
        driver = new ChromeDriver(options);

    }

    public String findAboutPage(String softwareName) {
        String serializedSoftwareName = softwareName.replace(" ", "+");
        String url = String.format("%s?searchTerm=%s", FUTUREPEDIA_URL, serializedSoftwareName);
        driver.get(url);
        List<WebElement> links = driver.findElements(By.cssSelector("a[data-sponsor-status='not_active']"));
        for (WebElement link : links) {
            if (link.getText().contains(softwareName)) {
                return link.getAttribute("href");
            }
        }
        throw new RuntimeException("Software name not found");
    }

    public String getDescription(String softwareName) {
        StringBuilder builder = new StringBuilder();
        List<WebElement> productDescriptionParts = driver.findElements(By.cssSelector(".MuiBox-root .css-0"));
        for (WebElement productDescriptionPart : productDescriptionParts) {
            if (productDescriptionPart.getText().contains(softwareName)) {
                builder.append(productDescriptionPart.getAttribute("innerHTML"));
            }
        }
        return builder.toString();
    }

    public String getShortSummary(String softwareName) {
        StringBuilder builder = new StringBuilder();
        List<WebElement> productShortText = driver.findElements(By.cssSelector(".MuiGrid-spacing-xs-4 p:nth-child(1)"));
        for (WebElement potentialProdSummary : productShortText) {
            if (potentialProdSummary.getText().contains(softwareName)) {
                builder.append(potentialProdSummary.getText());
            }
        }
        return builder.toString();
    }

    public List<String> getAlternatives() {
        List<WebElement> alternativeSoftwareElements = driver.findElements(By.cssSelector("a .MuiTypography-h6"));
        List<String> alternatives = new ArrayList<>(alternativeSoftwareElements.size());
        for (WebElement alternativeSoftwareElement : alternativeSoftwareElements) {
            String alternativeSoftwareName = alternativeSoftwareElement.getText();
            if (!alternativeSoftwareName.isBlank()) {
                alternatives.add(alternativeSoftwareName);
            }
        }
        return alternatives;
    }

    public String getImgURL(String softwareName) {
        String cssSelector = String.format("img[alt='%s']", softwareName);
        WebElement softwareImage = driver.findElement(By.cssSelector(cssSelector));
        return softwareImage.getAttribute("src");
    }

    public String getSoftwareURL() {
        String visitButtonXPath = "//*[text()='Visit']";
        WebElement visitButton = driver.findElement(By.xpath(visitButtonXPath));
        String completeURL = visitButton.getAttribute("href");
        return completeURL.split("\\?")[0];

    }


    public SoftwareDescription getSoftwareInfo(String softwareName) {
        String aboutPageURL = findAboutPage(softwareName);
        driver.get(aboutPageURL);
        String shortSummary = getShortSummary(softwareName);
        String softwareDescription = getDescription(softwareName);
        List<String> alternativeSoftware = getAlternatives();
        String imgURL = getImgURL(softwareName);
        String softwareURL = getSoftwareURL();
        return new SoftwareDescription(softwareName, softwareURL, alternativeSoftware, softwareDescription, imgURL, shortSummary);
    }


}
