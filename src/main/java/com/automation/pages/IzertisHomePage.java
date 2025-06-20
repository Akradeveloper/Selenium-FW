package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import java.util.List;

/**
 * Page Object para la página principal de Izertis
 */
public class IzertisHomePage extends BasePage {

    // Locators de la página principal
    @FindBy(id = "logoIzertis")
    private WebElement logo;

    @FindBy(className = "navbar-toggler collapsed")
    private WebElement mainNavigation;

    @FindBy(className = "btn-- btn--primary animated-button")
    private WebElement whatWeDoMenu;

    @FindBy(xpath = "//a[contains(text(),'Success stories') or contains(text(),'Casos de éxito')]")
    private WebElement successStoriesLink;

    @FindBy(xpath = "//a[contains(text(),'Publications') or contains(text(),'Publicaciones')]")
    private WebElement publicationsLink;

    @FindBy(xpath = "//a[contains(text(),'Who we are') or contains(text(),'Quiénes somos')]")
    private WebElement whoWeAreLink;

    @FindBy(xpath = "//a[contains(text(),'Contact') or contains(text(),'Contacto')]")
    private WebElement contactLink;

    @FindBy(xpath = "//a[contains(text(),'Work with us') or contains(text(),'Trabaja con nosotros')]")
    private WebElement workWithUsLink;

    @FindBy(css = "h1, .main-title, .hero-title")
    private WebElement mainTitle;

    @FindBy(xpath = "//h2[contains(text(),'Passion for Technology') or contains(text(),'Pasión por la Tecnología')]")
    private WebElement passionForTechnologyTitle;

    @FindBy(css = ".language-selector, .lang-switcher")
    private WebElement languageSelector;

    @FindBy(xpath = "//a[@href='/en/' or contains(@href,'language') or contains(text(),'EN')]")
    private WebElement englishLanguageOption;

    @FindBy(xpath = "//a[@href='/es/' or contains(text(),'ES')]")
    private WebElement spanishLanguageOption;

    @FindBy(className  = "iz--impulso-tecnologico container-fluid")
    private WebElement servicesSection;

    @FindBy(id = "navbar_com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet_INSTANCE_S1FlH3ijzYPN")
    private List<WebElement> serviceLinks;

    @FindBy(id = "gdpr-cookie-message")
    private WebElement cookieBanner;

    @FindBy(id = "gdpr-cookie-accept-all")
    private WebElement acceptCookiesButton;

    @FindBy(css = "footer, .footer")
    private WebElement footer;

    @FindBy(css = ".social-links a, .social-media a")
    private List<WebElement> socialMediaLinks;

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
     * Navega al menú "What do we do"
     */
    public void clickWhatWeDoMenu() {
        logger.info("Clickeando en el menú 'What do we do'");
        hoverOnElement(whatWeDoMenu);
        clickElement(whatWeDoMenu);
    }

    /**
     * Navega a Success Stories
     */
    public void clickSuccessStories() {
        logger.info("Navegando a Success Stories");
        clickElement(successStoriesLink);
    }

    /**
     * Navega a Publications
     */
    public void clickPublications() {
        logger.info("Navegando a Publications");
        clickElement(publicationsLink);
    }

    /**
     * Navega a Who we are
     */
    public void clickWhoWeAre() {
        logger.info("Navegando a Who we are");
        clickElement(whoWeAreLink);
    }

    /**
     * Navega a Contact
     */
    public void clickContact() {
        logger.info("Navegando a Contact");
        clickElement(contactLink);
    }

    /**
     * Navega a Work with us
     */
    public void clickWorkWithUs() {
        logger.info("Navegando a Work with us");
        clickElement(workWithUsLink);
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
     * Cambia el idioma a inglés
     */
    public void switchToEnglish() {
        logger.info("Cambiando idioma a inglés");
        try {
            if (isElementVisible(languageSelector)) {
                clickElement(languageSelector);
                Thread.sleep(1000); // Espera breve para que se abra el selector
            }
            if (isElementVisible(englishLanguageOption)) {
                clickElement(englishLanguageOption);
            } else {
                // Intenta buscar directamente el enlace en inglés
                clickElement(By.xpath("//a[contains(@href,'/en/') or contains(text(),'EN')]"));
            }
        } catch (Exception e) {
            logger.warn("No se pudo cambiar a inglés: " + e.getMessage());
        }
    }

    /**
     * Cambia el idioma a español
     */
    public void switchToSpanish() {
        logger.info("Cambiando idioma a español");
        try {
            if (isElementVisible(languageSelector)) {
                clickElement(languageSelector);
                Thread.sleep(1000); // Espera breve para que se abra el selector
            }
            if (isElementVisible(spanishLanguageOption)) {
                clickElement(spanishLanguageOption);
            } else {
                // Intenta buscar directamente el enlace en español
                clickElement(By.xpath("//a[contains(@href,'/es/') or contains(text(),'ES')]"));
            }
        } catch (Exception e) {
            logger.warn("No se pudo cambiar a español: " + e.getMessage());
        }
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
     * Hace scroll hacia la sección de servicios
     */
    public void scrollToServicesSection() {
        logger.info("Haciendo scroll hacia la sección de servicios");
        if (isElementVisible(servicesSection)) {
            scrollToElement(servicesSection);
        }
    }

    /**
     * Obtiene el número de enlaces de servicios disponibles
     */
    public int getServicesCount() {
        logger.info("Contando servicios disponibles");
        return serviceLinks.size();
    }

    /**
     * Clickea en un servicio específico por índice
     */
    public void clickServiceByIndex(int index) {
        logger.info("Clickeando en servicio en índice: {}", index);
        if (index >= 0 && index < serviceLinks.size()) {
            scrollToElement(serviceLinks.get(index));
            clickElement(serviceLinks.get(index));
        } else {
            logger.warn("Índice de servicio fuera de rango: {}", index);
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
     * Obtiene el número de enlaces de redes sociales
     */
    public int getSocialMediaLinksCount() {
        logger.info("Contando enlaces de redes sociales");
        return socialMediaLinks.size();
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