package com.automation.tests;

import com.automation.config.DriverManager;
import io.qameta.allure.Allure;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Clase base para todas las pruebas.
 * Su única responsabilidad es configurar y destruir el entorno de prueba (WebDriver).
 */
public abstract class BaseTest {

    protected void setUp() {
        DriverManager.initializeDriver();
    }

    protected void tearDown() {
        DriverManager.quitDriver();
    }
    
    /**
     * Método de utilidad para agregar un paso descriptivo al reporte de Allure.
     * @param description La descripción del paso.
     */
    protected void step(String description) {
        Allure.step(description);
    }
} 