# 🚀 Proyecto de Automatización Selenium - Izertis

Este proyecto contiene un framework de automatización desarrollado en Java con Selenium WebDriver para realizar pruebas automatizadas en el sitio web de [Izertis](https://www.izertis.com).

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Prerequisitos](#prerequisitos)
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración](#configuración)
- [Ejecución de Pruebas](#ejecución-de-pruebas)
- [Reportes](#reportes)
- [Buenas Prácticas](#buenas-prácticas)
- [Contribuir](#contribuir)

## ✨ Características

- **Page Object Model (POM)**: Implementación del patrón Page Object para mejor mantenibilidad
- **Cross-browser Testing**: Soporte para Chrome, Firefox y Edge
- **Configuración Flexible**: Configuración externa mediante archivos properties
- **Reportes Detallados**: Integración con Allure para reportes visuales
- **Logging Avanzado**: Sistema de logging con Logback
- **Screenshots Automáticos**: Capturas de pantalla automáticas en caso de fallos
- **Gestión Automática de Drivers**: WebDriverManager para gestión automática de drivers
- **TestNG Integration**: Framework de testing robusto con TestNG
- **Soporte para BDD**: Integración con Cucumber para pruebas basadas en comportamiento

## 🛠 Tecnologías Utilizadas

- **Java 17**
- **Selenium WebDriver 4.18.1**
- **Cucumber 7.15.0**
- **TestNG 7.9.0** (usado como runner)
- **Maven 3.x**
- **Allure 2.25.0** (Reportes)
- **WebDriverManager 5.7.0**
- **Logback 1.5.0** (Logging)
- **Allure CLI** (opcional, para reportes visuales)

## 📋 Prerequisitos

Antes de ejecutar este proyecto, asegúrate de tener instalado:

- ☕ **Java 17** o superior
- 📦 **Maven 3.6** o superior
- 🌐 **Google Chrome, Firefox o Microsoft Edge**
- 🔧 **Git** (para clonar el repositorio)
- 📊 **Allure CLI** (opcional, para reportes visuales)

### Verificar Instalaciones

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar Git
git --version

# Verificar Allure (opcional)
allure --version
```

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd izertis-selenium-automation
```

### 2. Instalar Dependencias

```bash
mvn clean install
```

### 3. Instalar Allure CLI (Opcional pero Recomendado)

Para generar reportes visuales atractivos:

**Mac (Homebrew):**
```bash
brew install allure
```

**Linux:**
```bash
# Descargar desde: https://docs.qameta.io/allure/#_installing_a_commandline
# O usar el instalador automático:
curl -o allure-2.34.0.tgz -Ls https://github.com/allure-framework/allure2/releases/download/2.34.0/allure-2.34.0.tgz
sudo tar -zxvf allure-2.34.0.tgz -C /opt/
sudo ln -s /opt/allure-2.34.0/bin/allure /usr/bin/allure
```

**Windows:**
```bash
# Usar Scoop:
scoop install allure

# O descargar manualmente desde GitHub
```

### 4. Verificar Instalación

```bash
mvn test -Dtest=IzertisHomePageTest#testHomePageLoads
```

## 📁 Estructura del Proyecto

```
izertis-selenium-automation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/automation/
│   │   │       ├── config/           # Configuraciones del framework
│   │   │       │   ├── Configuration.java
│   │   │       │   └── DriverManager.java
│   │   │       ├── pages/            # Page Objects
│   │   │       │   ├── BasePage.java
│   │   │       │   └── IzertisHomePage.java
│   │   │       └── utils/            # Utilidades
│   │   │           └── ElementUtils.java
│   │   └── resources/
│   │       ├── config.properties     # Configuración del framework
│   │       └── logback.xml          # Configuración de logging
│   └── test/
│       ├── java/
│       │   └── com/automation/
│       │       ├── runners/          # Test runners de Cucumber
│       │       │   └── TestRunner.java
│       │       └── stepdefinitions/  # Definiciones de pasos
│       │           └── IzertisStepDefinitions.java
│       └── resources/
│           └── features/             # Archivos .feature de Gherkin
│               └── izertis.feature
├── target/                          # Archivos generados
├── logs/                           # Archivos de log
├── pom.xml                         # Configuración de Maven
├── testng.xml                      # Configuración de TestNG
└── README.md                       # Este archivo
```

## 🥒 Gherkin / Cucumber

Este proyecto utiliza Cucumber para escribir los casos de prueba en un lenguaje natural llamado Gherkin.

- **Archivos Feature**: Se encuentran en `src/test/resources/features`.
- **Definiciones de Pasos**: Se encuentran en `src/test/java/com/automation/stepdefinitions`.

### Ejemplo de Escenario

```gherkin
Feature: Izertis Website

  Scenario: Visit Izertis home page and verify title
    Given I navigate to "https://www.izertis.com"
    Then I should see the page title as "Izertis | Transformación digital, innovación tecnológica y outsourcing"
```

## ⚙️ Configuración

### Archivo de Configuración

El archivo `src/main/resources/config.properties` contiene la configuración principal:

```properties
# URL Base
base.url=https://www.izertis.com

# Configuración del Navegador
browser=chrome
headless=false
maximize.window=true

# Timeouts (en segundos)
implicit.wait=10
explicit.wait=15
page.load.timeout=30
```

### Variables de Entorno

Puedes sobrescribir la configuración usando variables de entorno:

```bash
export BROWSER=firefox
export HEADLESS=true
export BASE_URL=https://www.izertis.com/en/
```

## 🧪 Ejecución de Pruebas

### Ejecutar Todas las Pruebas

```bash
mvn test
```

Este comando ejecutará todos los escenarios de Cucumber definidos en los archivos `.feature`.

### Ejecutar con Navegador Específico

```bash
mvn test -Dbrowser=firefox
```

### Ejecutar en Modo Headless

```bash
mvn test -Dheadless=true
```

## 📊 Reportes

### Generar Reportes de Allure

1. **Ejecutar las pruebas**:
```bash
mvn test
```

2. **Generar el reporte**:
```bash
mvn allure:report
```

3. **Servir el reporte localmente**:
```bash
mvn allure:serve
```

El reporte se abrirá automáticamente en tu navegador predeterminado.

### Alternativas sin Allure CLI

Si no tienes Allure CLI instalado, puedes usar el plugin de Maven:

```bash
# Ejecutar tests y servir reporte directamente
mvn test allure:serve

# O generar reporte estático
mvn allure:report
```

**Nota**: El plugin de Maven es más lento pero no requiere instalación adicional.

### Ubicación de Reportes

- **Resultados de Allure**: `allure-results/`
- **Reporte HTML**: `allure-report/`
- **Logs**: `logs/`
- **Screenshots**: Incluidos en el reporte de Allure

## 🎯 Casos de Prueba Incluidos

### IzertisHomePageTest

1. **testHomePageLoads**: Verifica que la página principal carga correctamente
2. **testMainUIElementsPresent**: Verifica que los elementos principales de UI están presentes
3. **testMainMenuNavigation**: Prueba la navegación del menú principal
4. **testServicesSection**: Verifica la sección de servicios
5. **testLanguageSwitching**: Prueba el cambio de idioma
6. **testFooterSection**: Verifica la sección del footer
7. **testPageSectionNavigation**: Prueba la navegación a diferentes secciones
8. **testLogoFunctionality**: Verifica la funcionalidad del logo

## 📝 Buenas Prácticas

### Escribir Nuevas Pruebas

1. **Extiende BaseTest**:
```java
public class NuevaPruebaTest extends BaseTest {
    // Tu código aquí
}
```

2. **Usa Page Objects**:
```java
@BeforeMethod
public void setUp() {
    homePage = new IzertisHomePage();
}
```

3. **Agrega Anotaciones de Allure**:
```java
@Test
@Story("Nueva Funcionalidad")
@Description("Descripción de la prueba")
@Severity(SeverityLevel.NORMAL)
public void testNuevaFuncionalidad() {
    // Tu código aquí
}
```

### Crear Nuevos Page Objects

1. **Extiende BasePage**:
```java
public class NuevaPagina extends BasePage {
    @Override
    public boolean isPageLoaded() {
        // Implementa la lógica de verificación
    }
    
    @Override
    public String getPageName() {
        return "Nueva Página";
    }
}
```

## 🐛 Solución de Problemas

### Problemas Comunes

1. **Driver no encontrado**:
   - WebDriverManager se encarga automáticamente de esto
   - Verifica tu conexión a internet

2. **Timeouts en elementos**:
   - Ajusta los timeouts en `config.properties`
   - Verifica que los locators sean correctos

3. **Tests fallan esporádicamente**:
   - Revisa los waits explícitos
   - Considera agregar pequeñas pausas donde sea necesario

### Logs y Debugging

- Revisa los logs en la carpeta `logs/`
- Los screenshots se incluyen automáticamente en los reportes
- Usa `logger.info()` para agregar información de debug

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/NuevaFuncionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/NuevaFuncionalidad`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 📞 Contacto

Para preguntas o sugerencias sobre este proyecto de automatización para Izertis:

- 📧 Email: [tu-email@ejemplo.com]
- 🐛 Issues: [GitHub Issues](link-to-issues)
- 📖 Documentación: [Wiki del Proyecto](link-to-wiki)

---

**¡Felices pruebas automatizadas! 🎉** 