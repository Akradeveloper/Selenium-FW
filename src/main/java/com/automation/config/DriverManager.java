package com.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Gestor de WebDriver para inicializar y gestionar diferentes navegadores
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static Configuration config = Configuration.getInstance();

    private DriverManager() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Inicializa el WebDriver según la configuración
     */
    public static void initializeDriver() {
        String browserName = config.getBrowser().toLowerCase();
        
        logger.info("Inicializando navegador: {}", browserName);
        
        WebDriver driver = switch (browserName) {
            case "chrome" -> initializeChromeDriver();
            case "firefox" -> initializeFirefoxDriver();
            case "edge" -> initializeEdgeDriver();
            default -> {
                logger.warn("Navegador no soportado: {}. Usando Chrome por defecto", browserName);
                yield initializeChromeDriver();
            }
        };

        // Configurar timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));

        // Maximizar ventana si está configurado
        if (config.shouldMaximizeWindow()) {
            driver.manage().window().maximize();
        }

        driverThreadLocal.set(driver);
        logger.info("WebDriver inicializado correctamente");
    }

    private static WebDriver initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        
        return new ChromeDriver(options);
    }

    private static WebDriver initializeFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }

    private static WebDriver initializeEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }

    /**
     * Obtiene la instancia actual del WebDriver
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver no está inicializado. Llama a initializeDriver() primero.");
        }
        return driver;
    }

    /**
     * Cierra y limpia el WebDriver
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver cerrado correctamente");
            } catch (Exception e) {
                logger.error("Error al cerrar WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
} 