package com.automation.tests;

import com.automation.pages.IzertisHomePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests para la página principal de Izertis
 */
@Epic("Izertis Website Automation")
@Feature("Home Page")
public class IzertisHomePageTest extends BaseTest {

    private IzertisHomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        super.setUp(); // 1. Inicializa el WebDriver
        homePage = new IzertisHomePage(); // 2. Inicializa la página (ahora con un driver válido)
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest() {
        super.tearDown();
    }

    @Test(priority = 1, description = "Verificar que la página principal de Izertis carga correctamente", groups = {"smoke"})
    @Story("Page Loading")
    @Severity(SeverityLevel.BLOCKER)
    public void testHomePageLoads() {
        Allure.step("Paso 1: Navegar a la página principal y aceptar cookies");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();

        Allure.step("Paso 2: Verificar que la página está cargada");
        Assert.assertTrue(homePage.isPageLoaded(), "La página principal no se cargó correctamente");

        Allure.step("Paso 3: Verificar el título y la URL de la página", () -> {
            Allure.step("Verificando el título de la página", () -> {
                String pageTitle = homePage.getPageTitle();
                Assert.assertTrue(pageTitle.toLowerCase().contains("izertis"),
                        "El título de la página '" + pageTitle + "' no contiene 'izertis'");
            });
            Allure.step("Verificando la URL actual", () -> {
                String currentUrl = homePage.getCurrentUrl();
                Assert.assertTrue(currentUrl.contains("izertis.com"),
                        "La URL '" + currentUrl + "' no contiene 'izertis.com'");
            });
        });
    }

    @Test(priority = 2, description = "Verificar que los elementos principales de la UI están presentes", groups = {"smoke"})
    @Story("UI Elements")
    @Severity(SeverityLevel.CRITICAL)
    public void testMainUIElementsPresent() {
        Allure.step("Paso 1: Navegar a la página principal y aceptar cookies");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();

        Allure.step("Paso 2: Verificar elementos visuales clave", () -> {
            Allure.step("Verificando que el logo es visible");
            Assert.assertTrue(homePage.isLogoVisible(), "El logo de Izertis no es visible");

            Allure.step("Verificando que la navegación principal es visible");
            Assert.assertTrue(homePage.isMainNavigationVisible(), "La navegación principal no es visible");

            Allure.step("Verificando el título principal de la página", () -> {
                String mainTitle = homePage.getMainTitle();
                Assert.assertFalse(mainTitle.isEmpty(), "El título principal está vacío.");
                Assert.assertFalse(mainTitle.equals("Título no encontrado"),
                        "No se pudo encontrar el título principal. Valor: " + mainTitle);
            });
        });
    }
}