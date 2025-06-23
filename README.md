# 🚀 Proyecto de Automatización Selenium - Izertis

Este proyecto contiene dos implementaciones diferentes de un framework de automatización desarrollado en Java con Selenium WebDriver para realizar pruebas automatizadas en el sitio web de [Izertis](https://www.izertis.com).

## 🌳 Ramas Disponibles

El proyecto está organizado en dos ramas principales, cada una implementando un enfoque diferente de automatización:

### 📝 [`without-cucumber`](../../tree/without-cucumber) - Enfoque TestNG Tradicional

Implementación clásica usando **TestNG** con Page Object Model.

**🎯 Ideal para:**
- Equipos familiarizados con TestNG
- Proyectos que requieren control granular de ejecución
- Casos de uso que no requieren BDD

**✨ Características:**
- Framework basado en **TestNG 7.9.0**
- Page Object Model (POM)
- Reportes con **Allure 2.25.0**
- Configuración flexible con properties
- Logging avanzado con Logback
- Screenshots automáticos en fallos
- Soporte multi-browser (Chrome, Firefox, Edge)

**🚀 Inicio Rápido:**
```bash
git checkout without-cucumber
./run-tests.sh --all              # Ejecutar todas las pruebas
./run-tests.sh --smoke --headless # Ejecutar pruebas smoke
./run-tests.sh --report           # Generar reporte Allure
```

---

### 🥒 [`with-cucumber`](../../tree/with-cucumber) - Enfoque BDD con Cucumber

Implementación usando **Cucumber** para Behavior-Driven Development (BDD).

**🎯 Ideal para:**
- Equipos que practican BDD
- Colaboración estrecha con stakeholders no técnicos
- Documentación ejecutable en lenguaje natural

**✨ Características:**
- Framework basado en **Cucumber-JVM**
- Escenarios escritos en **Gherkin** (Given-When-Then)
- Integration con **TestNG** como runner
- Reportes con **Allure** + reportes nativos de Cucumber
- Filtrado por tags (@smoke, @regression, etc.)
- Steps reutilizables y modulares

**🚀 Inicio Rápido:**
```bash
git checkout with-cucumber
./run-tests.sh                           # Ejecutar todos los escenarios
./run-tests.sh --tags "@smoke"           # Ejecutar solo escenarios @smoke
./run-tests.sh --tags "@smoke and not @wip" # Filtrado avanzado
./run-tests.sh --report                  # Generar reporte Allure
```

## 🤔 ¿Qué Rama Elegir?

| Criterio | without-cucumber | with-cucumber |
|----------|------------------|---------------|
| **Curva de Aprendizaje** | ⭐⭐⭐ Fácil | ⭐⭐⭐⭐ Media |
| **Colaboración con Business** | ⭐⭐ Limitada | ⭐⭐⭐⭐⭐ Excelente |
| **Flexibilidad Técnica** | ⭐⭐⭐⭐⭐ Máxima | ⭐⭐⭐⭐ Alta |
| **Documentación Ejecutable** | ⭐⭐ Básica | ⭐⭐⭐⭐⭐ Excelente |
| **Mantenimiento** | ⭐⭐⭐⭐ Fácil | ⭐⭐⭐ Moderado |

## 📋 Prerequisitos Comunes

Ambas ramas requieren:

- ☕ **Java 17** o superior
- 📦 **Maven 3.6** o superior  
- 🌐 **Chrome, Firefox o Edge**
- 🔧 **Git**
- 📊 **Allure CLI** (opcional, para reportes visuales)

### Verificar Instalaciones

```bash
java -version    # Debe mostrar Java 17+
mvn -version     # Debe mostrar Maven 3.6+
git --version    # Para clonar y cambiar ramas
allure --version # Opcional, para reportes visuales
```

## 🚀 Instalación Inicial

### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd izertis-selenium-automation
```

### 2. Instalar Allure CLI (Recomendado)

**Mac (Homebrew):**
```bash
brew install allure
```

**Linux/Windows:**
```bash
# Ver instrucciones en: https://docs.qameta.io/allure/#_installing_a_commandline
```

### 3. Elegir y Cambiar a una Rama

```bash
# Para enfoque TestNG tradicional
git checkout without-cucumber

# Para enfoque BDD con Cucumber  
git checkout with-cucumber
```

### 4. Instalar Dependencias

```bash
mvn clean install
```

### 5. Ejecutar Prueba de Verificación

```bash
# En rama without-cucumber
./run-tests.sh --all

# En rama with-cucumber  
./run-tests.sh
```

## 📊 Reportes y Resultados

Ambas ramas generan:

- **📋 Reportes de Allure**: `target/allure-report/`
- **📁 Resultados**: `target/allure-results/`
- **📝 Logs**: `logs/`
- **🖼️ Screenshots**: En reportes de Allure (automáticos en fallos)

## 🛠️ Scripts de Automatización

Ambas ramas incluyen el script `run-tests.sh` con funcionalidades:

- ✅ Limpieza automática de `target/` antes de cada ejecución
- 🌐 Selección de navegador (chrome, firefox, edge)
- 👻 Modo headless
- 📊 Generación automática de reportes
- 🎯 Filtrado de pruebas (por grupos o tags)

## 🔄 Cambiar Entre Ramas

```bash
# Ver ramas disponibles
git branch -a

# Cambiar a rama sin Cucumber
git checkout without-cucumber

# Cambiar a rama con Cucumber
git checkout with-cucumber

# Volver a main (este README)
git checkout main
```

## 📁 Estructura Común

```
izertis-selenium-automation/
├── src/main/java/com/automation/
│   ├── config/          # Configuración del framework
│   ├── pages/           # Page Objects  
│   └── utils/           # Utilidades comunes
├── src/test/java/com/automation/
│   ├── tests/           # Pruebas (TestNG) o Steps (Cucumber)
│   └── listeners/       # Listeners de Allure
├── target/              # Archivos generados
├── logs/               # Logs de ejecución
├── run-tests.sh        # Script de automatización
├── pom.xml             # Configuración Maven
└── testng.xml          # Configuración TestNG
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un Pull Request

---

## 🎯 Próximos Pasos

1. **Elige tu rama**: Decide entre `without-cucumber` o `with-cucumber`
2. **Haz checkout**: `git checkout [rama-elegida]`
3. **Lee el README específico**: Cada rama tiene documentación detallada
4. **Ejecuta las pruebas**: Usa `./run-tests.sh` para comenzar
5. **Explora los reportes**: Revisa los resultados en Allure

---

¿Necesitas ayuda para decidir? Revisa la [comparación detallada](#-qué-rama-elegir) arriba o explora ambas ramas para ver cuál se adapta mejor a tu proyecto. 