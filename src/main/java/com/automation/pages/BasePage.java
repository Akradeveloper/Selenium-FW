package com.automation.pages;

import com.automation.config.Configuration;
import com.automation.config.DriverManager;
import com.automation.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase base para todas las páginas con funcionalidad común
 */
public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected Configuration config;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.config = Configuration.getInstance();
        PageFactory.initElements(driver, this);
    }

    /**
     * Navega a una URL específica
     */
    public void navigateTo(String url) {
        logger.info("Navegando a: {}", url);
        driver.get(url);
    }

    /**
     * Navega a la URL base configurada
     */
    public void navigateToBase() {
        navigateTo(config.getBaseUrl());
    }

    /**
     * Obtiene el título de la página actual
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Título de página obtenido: {}", title);
        return title;
    }

    /**
     * Obtiene la URL actual
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("URL actual: {}", url);
        return url;
    }

    /**
     * Verifica si un elemento está presente en la página
     */
    protected boolean isElementPresent(By locator) {
        return ElementUtils.isElementPresent(locator);
    }

    /**
     * Verifica si un elemento está visible
     */
    protected boolean isElementVisible(WebElement element) {
        return ElementUtils.isElementVisible(element);
    }

    /**
     * Clickea un elemento
     */
    protected void clickElement(WebElement element) {
        ElementUtils.clickElement(element);
    }

    /**
     * Clickea un elemento usando locator
     */
    protected void clickElement(By locator) {
        ElementUtils.clickElement(locator);
    }

    /**
     * Envía texto a un elemento
     */
    protected void sendKeys(WebElement element, String text) {
        ElementUtils.sendKeys(element, text);
    }

    /**
     * Obtiene texto de un elemento
     */
    protected String getText(WebElement element) {
        return ElementUtils.getText(element);
    }

    /**
     * Hace scroll hacia un elemento
     */
    protected void scrollToElement(WebElement element) {
        ElementUtils.scrollToElement(element);
    }

    /**
     * Hace hover sobre un elemento
     */
    protected void hoverOnElement(WebElement element) {
        ElementUtils.hoverOnElement(element);
    }

    /**
     * Espera a que un elemento sea visible
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        return ElementUtils.waitForElementToBeVisible(locator);
    }

    /**
     * Espera a que un elemento sea clickeable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        return ElementUtils.waitForElementToBeClickable(locator);
    }

    /**
     * Verifica si la página actual es la esperada
     */
    public abstract boolean isPageLoaded();

    /**
     * Obtiene el nombre de la página
     */
    public abstract String getPageName();
} 