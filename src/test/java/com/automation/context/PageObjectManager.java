package com.automation.context;

import com.automation.pages.IzertisHomePage;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;
    private IzertisHomePage izertisHomePage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public IzertisHomePage getIzertisHomePage() {
        return (izertisHomePage == null) ? izertisHomePage = new IzertisHomePage(driver) : izertisHomePage;
    }
} 