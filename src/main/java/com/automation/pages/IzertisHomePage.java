package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object para la página principal de Izertis
 */
public class IzertisHomePage extends BasePage {

    // Locators de la página principal - Simplicados y robustos
    @FindBy(css = "img[alt*='Izertis'], .logo, [data-testid='logo']")
    private WebElement logo;

    @FindBy(css = "nav, .navbar, .main-navigation")
    private WebElement mainNavigation;

    @FindBy(css = "h1, h2, .main-title, .hero-title")
    private WebElement mainTitle;

    @FindBy(xpath = "//h1[contains(text(),'Passion') or contains(text(),'Technology') or contains(text(),'Pasión')] | //h2[contains(text(),'Passion') or contains(text(),'Technology')]")
    private WebElement passionForTechnologyTitle;

    @FindBy(id = "gdpr-cookie-message")
    private WebElement cookieBanner;

    @FindBy(id = "gdpr-cookie-accept-all")
    private WebElement acceptCookiesButton;

    @FindBy(css = "footer, .footer")
    private WebElement footer;

    // Métodos específicos de la página principal

    /**
     * Verifica si la página está cargada correctamente
     */
    @Override
    public boolean isPageLoaded() {
        try {
            return isElementVisible(logo) || 
                   driver.getTitle().toLowerCase().contains("izertis") ||
                   getCurrentUrl().contains("izertis.com");
        } catch (Exception e) {
            logger.error("Error verificando si la página está cargada: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el nombre de la página
     */
    @Override
    public String getPageName() {
        return "Izertis Home Page";
    }

    /**
     * Clickea en el logo de Izertis
     */
    public void clickLogo() {
        logger.info("Clickeando en el logo de Izertis");
        clickElement(logo);
    }


    /**
     * Obtiene el texto del título principal
     */
    public String getMainTitle() {
        logger.info("Obteniendo el título principal");
        if (isElementVisible(mainTitle)) {
            return getText(mainTitle);
        } else if (isElementVisible(passionForTechnologyTitle)) {
            return getText(passionForTechnologyTitle);
        }
        return "Título no encontrado";
    }

    /**
     * Acepta las cookies si aparece el banner
     */
    public void acceptCookiesIfPresent() {
        logger.info("Verificando y aceptando cookies si es necesario");
        try {
            if (isElementVisible(cookieBanner) && isElementVisible(acceptCookiesButton)) {
                clickElement(acceptCookiesButton);
                logger.info("Cookies aceptadas");
            }
        } catch (Exception e) {
            logger.info("No hay banner de cookies presente o ya fue aceptado");
        }
    }

    /**
     * Hace scroll hacia el footer
     */
    public void scrollToFooter() {
        logger.info("Haciendo scroll hacia el footer");
        if (isElementVisible(footer)) {
            scrollToElement(footer);
        }
    }

    /**
     * Verifica si el logo es visible
     */
    public boolean isLogoVisible() {
        return isElementVisible(logo);
    }

    /**
     * Verifica si la navegación principal es visible
     */
    public boolean isMainNavigationVisible() {
        return isElementVisible(mainNavigation);
    }
} 