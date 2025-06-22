package com.automation.utils;

import com.automation.config.Configuration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Utilidades para operaciones comunes con WebElement
 */
public class ElementUtils {
    private final Logger logger = LoggerFactory.getLogger(ElementUtils.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        int timeout = Configuration.getInstance().getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    /**
     * Espera a que un elemento sea clickeable y lo clickea
     */
    public void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.info("Elemento clickeado exitosamente");
        } catch (TimeoutException e) {
            logger.error("Timeout esperando que el elemento sea clickeable: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al clickear elemento: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea clickeable por locator y lo clickea
     */
    public void clickElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Elemento clickeado exitosamente usando locator: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Timeout esperando que el elemento sea clickeable: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al clickear elemento: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea visible y envía texto
     */
    public void sendKeys(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            logger.info("Texto enviado exitosamente: {}", text);
        } catch (TimeoutException e) {
            logger.error("Timeout esperando que el elemento sea visible: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al enviar texto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea visible por locator y envía texto
     */
    public void sendKeys(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            logger.info("Texto enviado exitosamente usando locator: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Timeout esperando que el elemento sea visible: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al enviar texto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea visible y obtiene su texto
     */
    public String getText(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getText();
            logger.info("Texto obtenido: {}", text);
            return text;
        } catch (TimeoutException e) {
            logger.error("Timeout esperando que el elemento sea visible: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener texto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Espera a que un elemento sea visible por locator y obtiene su texto
     */
    public String getText(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            logger.info("Texto obtenido usando locator {}: {}", locator, text);
            return text;
        } catch (TimeoutException e) {
            logger.error("Timeout esperando que el elemento sea visible: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener texto: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifica si un elemento está presente
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Verifica si un elemento está visible
     */
    public boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Espera a que un elemento sea visible
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Espera a que un elemento sea clickeable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Scroll hacia un elemento
     */
    public void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500); // Pequeña pausa para que el scroll termine
            logger.info("Scroll realizado hacia el elemento");
        } catch (Exception e) {
            logger.error("Error al hacer scroll: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Hover sobre un elemento
     */
    public void hoverOnElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            logger.info("Hover realizado sobre el elemento");
        } catch (Exception e) {
            logger.error("Error al hacer hover: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Selecciona una opción de un dropdown por texto visible
     */
    public void selectByVisibleText(WebElement selectElement, String text) {
        try {
            Select select = new Select(selectElement);
            select.selectByVisibleText(text);
            logger.info("Opción seleccionada: {}", text);
        } catch (Exception e) {
            logger.error("Error al seleccionar opción: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene todos los elementos que coinciden con el locator
     */
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
} 