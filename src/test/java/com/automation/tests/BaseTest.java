package com.automation.tests;

import com.automation.config.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

/**
 * Clase base para todas las pruebas automatizadas
 */
public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Setup que se ejecuta antes de cada suite de pruebas
     */
    @BeforeSuite
    public void beforeSuite() {
        logger.info("========== INICIANDO SUITE DE PRUEBAS IZERTIS ==========");
    }

    /**
     * Setup que se ejecuta antes de cada test
     */
    @BeforeMethod
    public void setUp(ITestResult result) {
        logger.info("Iniciando prueba: {}", result.getMethod().getMethodName());
        try {
            DriverManager.initializeDriver();
            logger.info("WebDriver inicializado correctamente para: {}", result.getMethod().getMethodName());
        } catch (Exception e) {
            logger.error("Error al inicializar WebDriver: " + e.getMessage());
            throw new RuntimeException("No se pudo inicializar el WebDriver", e);
        }
    }

    /**
     * Teardown que se ejecuta después de cada test
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        try {
            // Tomar screenshot si el test falló
            if (result.getStatus() == ITestResult.FAILURE) {
                logger.error("Test FALLIDO: {}", testName);
                takeScreenshot(testName);
                
                // Agregar información del error a Allure
                Allure.addAttachment("Error Details", result.getThrowable().getMessage());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                logger.info("Test EXITOSO: {}", testName);
            } else if (result.getStatus() == ITestResult.SKIP) {
                logger.warn("Test OMITIDO: {}", testName);
            }
            
        } catch (Exception e) {
            logger.error("Error en tearDown para test {}: {}", testName, e.getMessage());
        } finally {
            // Siempre cerrar el driver
            try {
                DriverManager.quitDriver();
                logger.info("WebDriver cerrado correctamente para: {}", testName);
            } catch (Exception e) {
                logger.error("Error al cerrar WebDriver: " + e.getMessage());
            }
        }
    }

    /**
     * Teardown que se ejecuta después de cada suite de pruebas
     */
    @AfterSuite
    public void afterSuite() {
        logger.info("========== FINALIZANDO SUITE DE PRUEBAS IZERTIS ==========");
    }

    /**
     * Toma un screenshot y lo adjunta al reporte de Allure
     */
    protected void takeScreenshot(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot - " + testName, new ByteArrayInputStream(screenshot));
            logger.info("Screenshot tomado para: {}", testName);
        } catch (Exception e) {
            logger.error("Error al tomar screenshot: " + e.getMessage());
        }
    }

    /**
     * Método de utilidad para tomar screenshot en cualquier momento
     */
    protected void takeScreenshot() {
        takeScreenshot("Manual Screenshot");
    }

    /**
     * Método de utilidad para agregar información adicional a Allure
     */
    protected void addAllureStep(String stepDescription) {
        Allure.step(stepDescription);
        logger.info("Paso de Allure: {}", stepDescription);
    }

    /**
     * Método de utilidad para agregar información adicional a Allure
     */
    protected void addAllureInfo(String name, String value) {
        Allure.addAttachment(name, value);
        logger.info("Información de Allure agregada - {}: {}", name, value);
    }
} 