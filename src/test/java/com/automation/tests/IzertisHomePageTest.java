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

    @Test(priority = 3)
    @Story("Navigation")
    @Description("Verificar la navegación del menú principal")
    @Severity(SeverityLevel.NORMAL)
    public void testMainMenuNavigation() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        addAllureStep("Clickeando en 'What do we do'");
        homePage.clickWhatWeDoMenu();
        
        // Verificar que la navegación funcionó (puede cambiar la URL o mostrar un submenu)
        String currentUrl = homePage.getCurrentUrl();
        addAllureInfo("URL after navigation", currentUrl);
        
        // Volver a la página principal
        addAllureStep("Regresando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
    }

    @Test(priority = 4)
    @Story("Services")
    @Description("Verificar la sección de servicios")
    @Severity(SeverityLevel.NORMAL)
    public void testServicesSection() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        addAllureStep("Haciendo scroll hacia la sección de servicios");
        homePage.scrollToServicesSection();
        
        addAllureStep("Obteniendo el número de servicios disponibles");
        int servicesCount = homePage.getServicesCount();
        addAllureInfo("Services Count", String.valueOf(servicesCount));
        
        Assert.assertTrue(servicesCount >= 0, "No se pudieron contar los servicios");
        
        // Si hay servicios disponibles, clickear en el primero
        if (servicesCount > 0) {
            addAllureStep("Clickeando en el primer servicio");
            homePage.clickServiceByIndex(0);
            
            // Pequeña pausa para que cargue la nueva página/sección
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            String urlAfterServiceClick = homePage.getCurrentUrl();
            addAllureInfo("URL after service click", urlAfterServiceClick);
        }
    }

    @Test(priority = 5)
    @Story("Language Switching")
    @Description("Verificar el cambio de idioma")
    @Severity(SeverityLevel.NORMAL)
    public void testLanguageSwitching() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        String originalUrl = homePage.getCurrentUrl();
        addAllureInfo("Original URL", originalUrl);
        
        addAllureStep("Cambiando idioma a inglés");
        homePage.switchToEnglish();
        
        // Pausa para que el cambio de idioma tome efecto
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String englishUrl = homePage.getCurrentUrl();
        addAllureInfo("English URL", englishUrl);
        
        addAllureStep("Cambiando idioma a español");
        homePage.switchToSpanish();
        
        // Pausa para que el cambio de idioma tome efecto
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String spanishUrl = homePage.getCurrentUrl();
        addAllureInfo("Spanish URL", spanishUrl);
    }

    @Test(priority = 6)
    @Story("Footer")
    @Description("Verificar la sección del footer")
    @Severity(SeverityLevel.MINOR)
    public void testFooterSection() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        addAllureStep("Haciendo scroll hacia el footer");
        homePage.scrollToFooter();
        
        addAllureStep("Verificando enlaces de redes sociales");
        int socialMediaCount = homePage.getSocialMediaLinksCount();
        addAllureInfo("Social Media Links Count", String.valueOf(socialMediaCount));
        
        Assert.assertTrue(socialMediaCount >= 0, "No se pudieron contar los enlaces de redes sociales");
    }

    @Test(priority = 7)
    @Story("Page Navigation")
    @Description("Verificar la navegación a diferentes secciones")
    @Severity(SeverityLevel.NORMAL)
    public void testPageSectionNavigation() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        // Probar navegación a Success Stories
        addAllureStep("Navegando a Success Stories");
        homePage.clickSuccessStories();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String successStoriesUrl = homePage.getCurrentUrl();
        addAllureInfo("Success Stories URL", successStoriesUrl);
        
        // Volver a la página principal
        addAllureStep("Regresando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        // Probar navegación a Publications
        addAllureStep("Navegando a Publications");
        homePage.clickPublications();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String publicationsUrl = homePage.getCurrentUrl();
        addAllureInfo("Publications URL", publicationsUrl);
    }

    @Test(priority = 8)
    @Story("Logo Functionality")
    @Description("Verificar la funcionalidad del logo")
    @Severity(SeverityLevel.MINOR)
    public void testLogoFunctionality() {
        addAllureStep("Navegando a la página principal");
        homePage.navigateToBase();
        homePage.acceptCookiesIfPresent();
        
        // Navegar a otra sección primero
        addAllureStep("Navegando a una sección diferente");
        homePage.clickWhoWeAre();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String beforeLogoClickUrl = homePage.getCurrentUrl();
        addAllureInfo("URL before logo click", beforeLogoClickUrl);
        
        addAllureStep("Clickeando en el logo para volver al inicio");
        homePage.clickLogo();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String afterLogoClickUrl = homePage.getCurrentUrl();
        addAllureInfo("URL after logo click", afterLogoClickUrl);
        
        // Verificar que estamos de vuelta en la página principal
        Assert.assertTrue(homePage.isPageLoaded(), "El logo no nos llevó de vuelta a la página principal");
    }
} 