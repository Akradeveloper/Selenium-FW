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
- [Casos de Prueba Incluidos](#casos-de-prueba-incluidos)
- [Buenas Prácticas](#buenas-prácticas)
- [Contribuir](#contribuir)

## ✨ Características

- **Page Object Model (POM)**: Implementación del patrón Page Object para mejor mantenibilidad.
- **Cross-browser Testing**: Soporte para Chrome, Firefox y Edge.
- **Configuración Flexible**: Configuración externa mediante archivos `config.properties`.
- **Reportes Detallados**: Integración con Allure para reportes visuales y descriptivos.
- **Logging Avanzado**: Sistema de logging con Logback para un seguimiento claro de la ejecución.
- **Listener de TestNG Centralizado**: Capturas de pantalla automáticas en fallos y gestión centralizada de eventos de test.
- **Gestión Automática de Drivers**: WebDriverManager para la descarga y gestión automática de binarios de drivers.
- **Framework de Testing Robusto**: Uso de TestNG con gestión de suites y grupos.

## 🛠 Tecnologías Utilizadas

- **Java 17**
- **Selenium WebDriver 4.18.1**
- **TestNG 7.9.0**
- **Maven 3.x**
- **Allure 2.25.0** (Reportes)
- **WebDriverManager 5.7.0**
- **Logback 1.5.0** (Logging)
- **SLF4J 2.0.12**

## 📋 Prerequisitos

- ☕ **Java 17** o superior
- 📦 **Maven 3.6** o superior
- 🌐 **Google Chrome, Firefox o Microsoft Edge**
- 🔧 **Git** (para clonar el repositorio)
- 📊 **Allure CLI** (opcional pero recomendado, para visualizar reportes)

## 🚀 Instalación

1.  **Clonar el Repositorio**:
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_DIRECTORIO>
    ```

2.  **Instalar Dependencias de Maven**:
    El siguiente comando descargará todas las dependencias definidas en el `pom.xml`.
    ```bash
    mvn clean install
    ```

3.  **Configurar Allure CLI (Opcional)**:
    Para generar y visualizar los reportes de Allure, se recomienda instalar su Command-Line Interface.
    -   **Windows (con Scoop)**: `scoop install allure`
    -   **Mac (con Homebrew)**: `brew install allure`
    -   **Linux**: Instrucciones en la [documentación oficial de Allure](https://docs.qameta.io/allure/#_installing_a_commandline).

## 📁 Estructura del Proyecto

```
Selenium-FW/
├── src/
│   ├── main/
│   │   ├── java/com/automation/
│   │   │   ├── config/           # Clases de configuración (DriverManager, Configuration)
│   │   │   ├── pages/            # Clases Page Object (BasePage, IzertisHomePage)
│   │   │   └── utils/            # Clases de utilidad (ElementUtils)
│   │   └── resources/
│   │       ├── config.properties # Archivo de propiedades para la configuración
│   │       └── logback.xml       # Configuración de logging
│   └── test/
│       ├── java/com/automation/
│       │   ├── listeners/        # Listeners de TestNG (AllureTestListener)
│       │   └── tests/            # Clases de prueba (BaseTest, IzertisHomePageTest)
│       └── resources/            # Recursos para tests (vacío por ahora)
├── logs/                         # Archivos de log generados
├── pom.xml                       # Dependencias y configuración de Maven
├── testng.xml                    # Definición de suites de TestNG
└── README.md                     # Este archivo
```

## ⚙️ Configuración

El framework se configura principalmente a través del archivo `src/main/resources/config.properties`.

```properties
# URL base para las pruebas
base.url=https://www.izertis.com

# Configuración del navegador (chrome, firefox, edge)
browser=chrome
headless=false
maximize.window=true

# Timeouts (en segundos)
implicit.wait=10
explicit.wait=15
page.load.timeout=30
```

Puedes sobrescribir estos valores al ejecutar los tests desde la línea de comandos usando `-Dpropiedad=valor`.

## 🧪 Ejecución de Pruebas

El proyecto utiliza Maven para gestionar el ciclo de vida de la compilación y ejecución de pruebas.

### Ejecutar todas las pruebas

Este comando ejecuta la suite por defecto definida en `pom.xml` (que apunta a `testng.xml`).
```bash
mvn clean test
```

### Ejecutar un grupo específico de pruebas (ej. Smoke)

Utiliza la propiedad `-Dgroups` para ejecutar solo los tests que pertenezcan a un grupo específico (definido en el código con `@Test(groups = "...")`).
```bash
# Ejecuta solo los tests del grupo "smoke"
mvn clean test -Dgroups=smoke
```

### Ejecutar una clase de prueba específica

```bash
mvn clean test -Dtest=IzertisHomePageTest
```

### Ejecutar un método de prueba específico

```bash
mvn clean test -Dtest=IzertisHomePageTest#testHomePageLoads
```

### Pasar parámetros desde la línea de comandos

Puedes sobrescribir los parámetros del navegador o la URL base.
```bash
# Ejecutar en Firefox en modo headless
mvn clean test -Dbrowser=firefox -Dheadless=true

# Ejecutar en una URL diferente
mvn clean test -Dbase.url=https://www.google.com
```

## 📊 Reportes

La integración con Allure permite generar reportes detallados y visuales.

1.  **Ejecutar las pruebas**:
    Esto generará los archivos de resultados en el directorio `target/allure-results`.
    ```bash
    mvn clean test
    ```

2.  **Generar y visualizar el reporte**:
    Este comando procesa los resultados y levanta un servidor web local para mostrar el reporte.
    ```bash
    mvn allure:serve
    ```
    El reporte se abrirá automáticamente en tu navegador.

## 🎯 Casos de Prueba Incluidos

La suite actual contiene las siguientes pruebas para la página principal de Izertis:

### `IzertisHomePageTest`

1.  **`testHomePageLoads`** (`@group="smoke"`)
    -   **Descripción**: Verifica que la página principal de Izertis carga correctamente.
    -   **Pasos**:
        -   Navega a la URL base.
        -   Acepta las cookies si el banner está presente.
        -   Verifica que la página se considera "cargada".
        -   Valida que el título de la página y la URL son correctos.

2.  **`testMainUIElementsPresent`** (`@group="smoke"`)
    -   **Descripción**: Verifica que los elementos principales de la interfaz de usuario están presentes en la página principal.
    -   **Pasos**:
        -   Navega a la URL base y acepta cookies.
        -   Verifica que el logo de Izertis es visible.
        -   Verifica que el menú de navegación principal es visible.
        -   Verifica que el título principal de la página se muestra y no está vacío.

## 📝 Buenas Prácticas

### Escribir Nuevas Pruebas

1.  **Crea una nueva clase de Test**: La clase debe terminar en `Test` y extender `BaseTest`.
    ```java
    public class MiNuevaPruebaTest extends BaseTest {
        // ...
    }
    ```

2.  **Sigue el patrón de inicialización**: Usa `@BeforeMethod` y `@AfterMethod` para controlar el ciclo de vida del driver y de las páginas.
    ```java
    public class MiNuevaPruebaTest extends BaseTest {
        private MiPagina miPagina;

        @BeforeMethod(alwaysRun = true)
        public void setUpTest() {
            super.setUp(); // Primero inicializa el driver
            miPagina = new MiPagina(); // Luego inicializa el Page Object
        }

        @AfterMethod(alwaysRun = true)
        public void tearDownTest() {
            super.tearDown();
        }

        @Test(description = "Descripción de mi nueva prueba")
        public void miPrueba() {
            // Lógica de la prueba usando miPagina
        }
    }
    ```

3.  **Usa anotaciones de Allure**: Describe tus pruebas con `@Description`, `@Epic`, `@Feature` y `@Story` para enriquecer los reportes. Usa `Allure.step()` para detallar los pasos.

### Crear Nuevos Page Objects

1.  **Crea una nueva clase Page**: La clase debe extender `BasePage`.
2.  **Define los localizadores**: Usa la anotación `@FindBy` para definir los `WebElement`.
3.  **Implementa los métodos abstractos**: `isPageLoaded()` y `getPageName()`.
4.  **Añade métodos de acción**: Crea métodos públicos que encapsulen las interacciones con los elementos de la página.

## 🐛 Solución de Problemas

-   **`WebDriver no está inicializado` o `NullPointerException`**: Asegúrate de que tu método `@BeforeMethod` en la clase de test llama a `super.setUp()` **antes** de inicializar cualquier Page Object.
-   **Tests fallan esporádicamente (Flakiness)**: Revisa los waits en `ElementUtils`. Puede que necesites un `waitForElementToBeVisible` o `waitForElementToBeClickable` antes de interactuar con un elemento.
-   **El reporte de Allure está vacío**: Asegúrate de que el `aspectjweaver` está configurado correctamente en el `maven-surefire-plugin` dentro de tu `pom.xml`.

---

**¡Felices pruebas automatizadas! 🎉** 