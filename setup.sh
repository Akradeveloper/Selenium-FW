#!/bin/bash

# Script de configuración para el proyecto de automatización Izertis
# ==================================================================

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Izertis Automation Setup ===${NC}"
echo ""

# Verificar sistema operativo
OS="$(uname -s)"
case "${OS}" in
    Linux*)     MACHINE=Linux;;
    Darwin*)    MACHINE=Mac;;
    CYGWIN*)    MACHINE=Cygwin;;
    MINGW*)     MACHINE=MinGw;;
    *)          MACHINE="UNKNOWN:${OS}"
esac

echo -e "${YELLOW}Sistema detectado: $MACHINE${NC}"
echo ""

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar Java
echo -e "${BLUE}Verificando Java...${NC}"
if command_exists java; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    echo -e "${GREEN}✓ Java encontrado: $JAVA_VERSION${NC}"
    
    # Verificar si es Java 8 o superior
    MAJOR_VERSION=$(echo $JAVA_VERSION | cut -d'.' -f1)
    if [ "$MAJOR_VERSION" -ge 8 ] 2>/dev/null || [ "$MAJOR_VERSION" = "1" ]; then
        if [ "$MAJOR_VERSION" = "1" ]; then
            MINOR_VERSION=$(echo $JAVA_VERSION | cut -d'.' -f2)
            if [ "$MINOR_VERSION" -ge 8 ]; then
                echo -e "${GREEN}✓ Versión de Java compatible${NC}"
            else
                echo -e "${RED}✗ Se requiere Java 8 o superior${NC}"
            fi
        else
            echo -e "${GREEN}✓ Versión de Java compatible${NC}"
        fi
    fi
else
    echo -e "${RED}✗ Java no encontrado${NC}"
    echo -e "${YELLOW}Por favor instala Java 17 o superior:${NC}"
    if [ "$MACHINE" = "Mac" ]; then
        echo -e "  brew install openjdk@17"
        echo -e "  o descarga desde: https://adoptopenjdk.net/"
    elif [ "$MACHINE" = "Linux" ]; then
        echo -e "  sudo apt-get install openjdk-17-jdk  # Ubuntu/Debian"
        echo -e "  sudo yum install java-17-openjdk-devel  # CentOS/RHEL"
    fi
fi

echo ""

# Verificar Maven
echo -e "${BLUE}Verificando Maven...${NC}"
if command_exists mvn; then
    MAVEN_VERSION=$(mvn -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓ Maven encontrado: $MAVEN_VERSION${NC}"
else
    echo -e "${RED}✗ Maven no encontrado${NC}"
    echo -e "${YELLOW}Por favor instala Maven:${NC}"
    if [ "$MACHINE" = "Mac" ]; then
        echo -e "  brew install maven"
    elif [ "$MACHINE" = "Linux" ]; then
        echo -e "  sudo apt-get install maven  # Ubuntu/Debian"
        echo -e "  sudo yum install maven  # CentOS/RHEL"
    fi
    echo -e "  o descarga desde: https://maven.apache.org/download.cgi"
fi

echo ""

# Verificar Git
echo -e "${BLUE}Verificando Git...${NC}"
if command_exists git; then
    GIT_VERSION=$(git --version)
    echo -e "${GREEN}✓ Git encontrado: $GIT_VERSION${NC}"
else
    echo -e "${YELLOW}⚠ Git no encontrado (opcional para este proyecto)${NC}"
fi

echo ""

# Verificar Allure CLI
echo -e "${BLUE}Verificando Allure CLI...${NC}"
if command_exists allure; then
    ALLURE_VERSION=$(allure --version)
    echo -e "${GREEN}✓ Allure CLI encontrado: $ALLURE_VERSION${NC}"
else
    echo -e "${YELLOW}⚠ Allure CLI no encontrado${NC}"
    echo -e "${YELLOW}Para generar reportes visuales, instala Allure CLI:${NC}"
    if [ "$MACHINE" = "Mac" ]; then
        echo -e "  brew install allure"
    elif [ "$MACHINE" = "Linux" ]; then
        echo -e "  # Descargar desde: https://docs.qameta.io/allure/#_installing_a_commandline"
    fi
    echo -e "  o usar Maven plugin: mvn allure:serve"
fi

echo ""

# Verificar navegadores
echo -e "${BLUE}Verificando navegadores...${NC}"

# Chrome
if [ "$MACHINE" = "Mac" ]; then
    if [ -d "/Applications/Google Chrome.app" ]; then
        echo -e "${GREEN}✓ Google Chrome encontrado${NC}"
    else
        echo -e "${YELLOW}⚠ Google Chrome no encontrado${NC}"
    fi
elif [ "$MACHINE" = "Linux" ]; then
    if command_exists google-chrome || command_exists chromium-browser; then
        echo -e "${GREEN}✓ Chrome/Chromium encontrado${NC}"
    else
        echo -e "${YELLOW}⚠ Chrome/Chromium no encontrado${NC}"
    fi
fi

# Firefox
if command_exists firefox; then
    echo -e "${GREEN}✓ Firefox encontrado${NC}"
else
    echo -e "${YELLOW}⚠ Firefox no encontrado${NC}"
fi

echo ""

# Crear directorios necesarios si no existen
echo -e "${BLUE}Verificando estructura de directorios...${NC}"
mkdir -p logs
mkdir -p allure-results
mkdir -p allure-report

echo -e "${GREEN}✓ Directorios creados/verificados${NC}"

echo ""

# Verificar archivos del proyecto
echo -e "${BLUE}Verificando archivos del proyecto...${NC}"

PROJECT_FILES=(
    "pom.xml"
    "testng.xml"
    "src/main/java/com/automation/config/Configuration.java"
    "src/main/java/com/automation/config/DriverManager.java"
    "src/main/java/com/automation/pages/BasePage.java"
    "src/main/java/com/automation/pages/IzertisHomePage.java"
    "src/main/java/com/automation/utils/ElementUtils.java"
    "src/test/java/com/automation/tests/BaseTest.java"
    "src/test/java/com/automation/tests/IzertisHomePageTest.java"
    "src/main/resources/config.properties"
    "src/main/resources/logback.xml"
)

for file in "${PROJECT_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓ $file${NC}"
    else
        echo -e "${RED}✗ $file${NC}"
    fi
done

echo ""

# Intentar compilar si Maven está disponible
if command_exists mvn && command_exists java; then
    echo -e "${BLUE}Intentando compilar el proyecto...${NC}"
    mvn clean compile -q
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Proyecto compilado exitosamente${NC}"
    else
        echo -e "${RED}✗ Error al compilar el proyecto${NC}"
        echo -e "${YELLOW}Ejecuta 'mvn clean compile' para ver los detalles del error${NC}"
    fi
else
    echo -e "${YELLOW}⚠ No se puede compilar - Maven o Java no están disponibles${NC}"
fi

echo ""

# Resumen
echo -e "${BLUE}=== Resumen ===${NC}"
if command_exists java && command_exists mvn; then
    echo -e "${GREEN}✓ El proyecto está listo para ejecutarse${NC}"
    echo ""
    echo -e "${YELLOW}Comandos disponibles:${NC}"
    echo -e "  ./run-tests.sh --help    # Ver opciones disponibles"
    echo -e "  ./run-tests.sh --all     # Ejecutar todas las pruebas"
    echo -e "  ./run-tests.sh --smoke   # Ejecutar pruebas de smoke"
    echo -e "  mvn test                 # Ejecutar con Maven directamente"
else
    echo -e "${RED}✗ Faltan dependencias requeridas${NC}"
    echo -e "${YELLOW}Por favor instala Java y Maven antes de continuar${NC}"
fi

echo ""
echo -e "${BLUE}¡Configuración completa!${NC}" 