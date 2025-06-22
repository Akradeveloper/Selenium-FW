package com.automation.pages;

import com.automation.config.Configuration;
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
    protected ElementUtils elementUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.config = Configuration.getInstance();
        this.elementUtils = new ElementUtils(driver);
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
        return elementUtils.isElementPresent(locator);
    }

    /**
     * Verifica si un elemento está visible
     */
    protected boolean isElementVisible(WebElement element) {
        return elementUtils.isElementVisible(element);
    }

    /**
     * Clickea un elemento
     */
    protected void clickElement(WebElement element) {
        elementUtils.clickElement(element);
    }

    /**
     * Clickea un elemento usando locator
     */
    protected void clickElement(By locator) {
        elementUtils.clickElement(locator);
    }

    /**
     * Envía texto a un elemento
     */
    protected void sendKeys(WebElement element, String text) {
        elementUtils.sendKeys(element, text);
    }

    /**
     * Obtiene texto de un elemento
     */
    protected String getText(WebElement element) {
        return elementUtils.getText(element);
    }

    /**
     * Hace scroll hacia un elemento
     */
    protected void scrollToElement(WebElement element) {
        elementUtils.scrollToElement(element);
    }

    /**
     * Hace hover sobre un elemento
     */
    protected void hoverOnElement(WebElement element) {
        elementUtils.hoverOnElement(element);
    }

    /**
     * Espera a que un elemento sea visible
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        return elementUtils.waitForElementToBeVisible(locator);
    }

    /**
     * Espera a que un elemento sea clickeable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        return elementUtils.waitForElementToBeClickable(locator);
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