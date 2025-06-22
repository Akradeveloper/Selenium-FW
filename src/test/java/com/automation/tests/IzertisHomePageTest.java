package com.automation.tests;

import com.automation.pages.IzertisHomePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests para la página principal de Izertis
 */
@Epic("Izertis Website Automation")
@Feature("Home Page")
public class IzertisHomePageTest extends BaseTest {

    private IzertisHomePage homePage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void setUpHomePage() {
        homePage = new IzertisHomePage();
    }

    @Test(priority = 1)
    @Story("Page Loading")
    @Description("Verificar que la página principal de Izertis carga correctamente")
    @Severity(SeverityLevel.BLOCKER)
    public void testHomePageLoads() {
        addAllureStep("Navegando a la página principal de Izertis");
        homePage.navigateToBase();
        
        addAllureStep("Aceptando cookies si aparece el banner");
        homePage.acceptCookiesIfPresent();
        
        addAllureStep("Verificando que la página está cargada");
        Assert.assertTrue(homePage.isPageLoaded(), "La página principal no se cargó correctamente");
        
        addAllureStep("Verificando el título de la página");
        String pageTitle = homePage.getPageTitle();
        addAllureInfo("Page Title", pageTitle);
        Assert.assertTrue(pageTitle.toLowerCase().contains("izertis"), 
                         "El título de la página no contiene 'Izertis'");
        
        addAllureStep("Verificando la URL actual");
        String currentUrl = homePage.getCurrentUrl();
        addAllureInfo("Current URL", currentUrl);
        Assert.assertTrue(currentUrl.contains("izertis.com"), 
                         "La URL no contiene 'izertis.com'");
    }

    @Test(priority = 2)
    @Story("UI Elements")
    @Description("Verificar que los elementos principales de la UI están presentes")
    @Severity(SeverityLevel.CRITICAL)
    public void testMainUIElementsPresent() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        addAllureStep("Verificando que el logo es visible");
        Assert.assertTrue(homePage.isLogoVisible(), "El logo de Izertis no es visible");
        
        addAllureStep("Verificando que la navegación principal es visible");
        Assert.assertTrue(homePage.isMainNavigationVisible(), "La navegación principal no es visible");
        
        addAllureStep("Obteniendo el título principal de la página");
        String mainTitle = homePage.getMainTitle();
        addAllureInfo("Main Title", mainTitle);
        Assert.assertFalse(mainTitle.equals("Título no encontrado"), 
                          "No se pudo encontrar el título principal");
    }
}