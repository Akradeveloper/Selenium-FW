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
- **Soporte para BDD**: Integración con Cucumber para pruebas basadas en comportamiento
- **Inyección de Dependencias**: Uso de PicoContainer para gestionar el estado y aislar escenarios.

## 🛠 Tecnologías Utilizadas

- **Java 17**
- **Selenium WebDriver 4.18.1**
- **Cucumber 7.15.0**
- **TestNG 7.9.0** (usado como runner)
- **PicoContainer** (para inyección de dependencias)
- **Maven 3.x**
- **Allure 2.25.0** (Reportes)
- **WebDriverManager 5.7.0**
- **Logback 1.5.0** (Logging)

## 📋 Prerequisitos

- ☕ **Java 17** o superior
- 📦 **Maven 3.6** o superior
- 🌐 **Google Chrome, Firefox o Microsoft Edge**
- 🔧 **Git** (para clonar el repositorio)
- 📊 **Allure CLI** (opcional, para reportes visuales)

## 🚀 Instalación

1.  **Clonar el Repositorio**: `git clone <repository-url>`
2.  **Instalar Dependencias**: `mvn clean install`

## 📁 Estructura del Proyecto

```
izertis-selenium-automation/
├── src/
│   ├── main/
│   │   ├── java/com/automation/
│   │   │   ├── config/      # Configuración de drivers y properties
│   │   │   ├── pages/       # Clases Page Object
│   │   │   └── utils/       # Clases de utilidad (ej: ElementUtils)
│   │   └── resources/
│   │       ├── config.properties
│   │       └── logback.xml
│   └── test/
│       ├── java/com/automation/
│       │   ├── context/     # Gestión de estado del escenario (TestContext)
│       │   ├── hooks/       # Hooks de Cucumber (@Before, @After)
│       │   ├── runners/     # Runner de TestNG para Cucumber
│       │   └── stepdefinitions/ # Clases con la implementación de los pasos
│       └── resources/
│           └── features/    # Archivos Gherkin (.feature)
├── logs/
├── target/                  # Carpeta de compilación y reportes
├── pom.xml
├── testng.xml
├── run-tests.sh             # Script para ejecutar pruebas
└── README.md
```

## ⚙️ Configuración

La configuración principal se encuentra en `src/main/resources/config.properties`. Puedes sobrescribir estas propiedades pasando variables de Maven en la línea de comandos (ej: `-Dbrowser=firefox`).

## 🧪 Ejecución de Pruebas

La forma recomendada de ejecutar las pruebas es a través del script `run-tests.sh`.

### Usando el Script `run-tests.sh`

```bash
# Dar permisos de ejecución (solo la primera vez)
chmod +x run-tests.sh

# Ejecutar todos los escenarios
./run-tests.sh

# Ejecutar solo los escenarios marcados con el tag @smoke
./run-tests.sh --tags "@smoke"

# Ejecutar escenarios que sean @smoke Y NO sean @wip (work in progress)
./run-tests.sh --tags "@smoke and not @wip"

# Ejecutar con un navegador específico
./run-tests.sh --browser firefox

# Generar y abrir el último reporte de Allure
./run-tests.sh --report
```

### Usando Maven Directamente

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar solo los escenarios con el tag @smoke
mvn test -Dcucumber.filter.tags="@smoke"
```

## 📊 Reportes

Los reportes de Allure se generan dentro de la carpeta `target`.

-   **Resultados crudos**: `target/allure-results/`
-   **Reporte HTML**: `target/allure-report/`

Para generar y ver el reporte, ejecuta `./run-tests.sh --report` o `mvn allure:serve`.

## 📝 Buenas Prácticas

### 1. Escribir Escenarios en Gherkin

Define el comportamiento en los ficheros `.feature` usando un lenguaje claro y declarativo.

```gherkin
@smoke
Scenario: Visitar la página y verificar el título
  Given navego a "https://www.izertis.com"
  Then debería ver que el título de la página es "Izertis | Transformación digital, innovación tecnológica y outsourcing"
```

### 2. Implementar Step Definitions

Los *steps* deben ser una fina capa de orquestación que llama a los Page Objects. **No deben contener lógica de Selenium**.

```java
// En una clase de Step Definitions
@Then("debería ver que el título de la página es {string}")
public void deberia_ver_que_el_titulo_es(String expectedTitle) {
    String actualTitle = izertisHomePage.getPageTitle();
    Assert.assertEquals(actualTitle, expectedTitle);
}
```

### 3. Usar Page Objects

Toda la interacción con una página (localizadores y métodos de acción) debe estar encapsulada en su respectiva clase Page Object.

```java
// En una clase Page Object
public String getPageTitle() {
    return driver.getTitle();
}
```

### 4. Gestión de Estado con Inyección de Dependencias

-   **`TestContext`**: Mantiene el estado de un escenario (driver, page objects). Es inyectado por PicoContainer en los constructores de los Hooks y Steps.
-   **`Hooks`**: Configuran y destruyen el entorno de prueba (`@Before`, `@After`), y realizan acciones transversales como tomar capturas de pantalla en fallos.

Este enfoque asegura que cada escenario se ejecute de forma aislada y segura, eliminando por completo el uso de `static` para gestionar el estado.

## 🐛 Solución de Problemas

-   **El navegador no se cierra al final**: Asegúrate de que el paquete `com.automation.hooks` esté incluido en la propiedad `glue` de la clase `TestRunner`. Si los hooks no se ejecutan, el `driver.quit()` nunca se llamará.
-   **`NullPointerException` al acceder a Page Objects**: Verifica que `TestContext` y `PageObjectManager` estén correctamente configurados para la inyección de dependencias. Cada clase de `steps` y `hooks` debe recibir el `TestContext` en su constructor.
-   **El reporte de Allure no muestra los nombres de Features/Scenarios**: Confirma que estás usando la dependencia `allure-cucumber7-jvm` y que el plugin está añadido en las `@CucumberOptions` del `TestRunner`. El listener de `allure-testng` debe ser eliminado del `testng.xml`.

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