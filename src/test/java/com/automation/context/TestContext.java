package com.automation.context;

import com.automation.config.DriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContext {
    private WebDriver driver;
    private PageObjectManager pageObjectManager;
    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);

    public WebDriver getDriver() {
        if (driver == null) {
            logger.info("Creando una nueva instancia de WebDriver para el contexto de prueba.");
            driver = DriverManager.createDriver();
        }
        return driver;
    }

    public PageObjectManager getPageObjectManager() {
        if (pageObjectManager == null) {
            pageObjectManager = new PageObjectManager(getDriver());
        }
        return pageObjectManager;
    }

    public void quitDriver() {
        if (driver != null) {
            logger.info("Cerrando la instancia de WebDriver del contexto de prueba.");
            driver.quit();
            driver = null;
        } else {
            logger.warn("Se intentó cerrar un WebDriver, pero la instancia ya era nula.");
        }
    }
} 