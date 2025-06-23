#!/bin/bash

# Script para ejecutar pruebas de automatización de Izertis
# =========================================================

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para mostrar ayuda
show_help() {
    echo -e "${BLUE}=== Izertis Automation Test Runner ===${NC}"
    echo ""
    echo "Uso: $0 [opciones]"
    echo ""
    echo "Opciones:"
    echo "  -h, --help              Mostrar esta ayuda"
    echo "  -a, --all               Ejecutar todas las pruebas"
    echo "  -s, --smoke             Ejecutar solo pruebas de smoke"
    echo "  -b, --browser BROWSER   Especificar navegador (chrome, firefox, edge)"
    echo "  -e, --headless          Ejecutar en modo headless"
    echo "  -r, --report            Generar y abrir reporte de Allure"
    echo "  -c, --clean             Limpiar archivos de compilación"
    echo ""
    echo "Ejemplos:"
    echo "  $0 --all                # Ejecutar todas las pruebas"
    echo "  $0 --smoke --headless   # Ejecutar pruebas de smoke en modo headless"
    echo "  $0 --browser firefox    # Ejecutar con Firefox"
    echo "  $0 --report             # Solo generar reporte"
}

# Función para limpiar archivos
clean_project() {
    echo -e "${YELLOW}Limpiando proyecto...${NC}"
    mvn clean
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Proyecto limpiado exitosamente${NC}"
    else
        echo -e "${RED}✗ Error al limpiar el proyecto${NC}"
        exit 1
    fi
}

# Función para ejecutar pruebas
run_tests() {
    local test_type=$1
    local browser=$2
    local headless=$3
    
    echo -e "${BLUE}Ejecutando pruebas de automatización...${NC}"
    echo -e "${YELLOW}Configuración:${NC}"
    echo -e "  Tipo: $test_type"
    echo -e "  Navegador: $browser"
    echo -e "  Headless: $headless"
    echo ""
    
    # Limpiar proyecto antes de ejecutar pruebas
    echo -e "${YELLOW}Limpiando carpeta target...${NC}"
    mvn clean
    if [ $? -ne 0 ]; then
        echo -e "${RED}✗ Error al limpiar el proyecto${NC}"
        return 1
    fi
    echo -e "${GREEN}✓ Carpeta target limpiada${NC}"
    echo ""
    
    # Construir comando Maven
    local mvn_cmd="mvn test"
    
    if [ "$test_type" = "smoke" ]; then
        mvn_cmd="$mvn_cmd -DsuiteXmlFile=testng.xml -Dtest.type=smoke"
    fi
    
    if [ "$browser" != "default" ]; then
        mvn_cmd="$mvn_cmd -Dbrowser=$browser"
    fi
    
    if [ "$headless" = "true" ]; then
        mvn_cmd="$mvn_cmd -Dheadless=true"
    fi
    
    echo -e "${YELLOW}Ejecutando: $mvn_cmd${NC}"
    echo ""
    
    # Ejecutar comando
    eval $mvn_cmd
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Pruebas ejecutadas exitosamente${NC}"
        return 0
    else
        echo -e "${RED}✗ Algunas pruebas fallaron${NC}"
        return 1
    fi
}

# Función para generar reporte de Allure
generate_report() {
    echo -e "${BLUE}Generando reporte de Allure...${NC}"
    
    # Verificar si existen resultados de Allure
    if [ ! -d "target/allure-results" ] || [ -z "$(ls -A target/allure-results 2>/dev/null)" ]; then
        echo -e "${RED}✗ No se encontraron resultados de Allure en 'target/allure-results/'${NC}"
        echo -e "${YELLOW}Ejecuta primero las pruebas con: ./run-tests.sh --all${NC}"
        return 1
    fi
    
    # Verificar si Allure CLI está instalado
    if ! command -v allure &> /dev/null; then
        echo -e "${YELLOW}Allure CLI no está instalado. Opciones disponibles:${NC}"
        echo -e "${BLUE}1. Instalar Allure CLI:${NC}"
        echo -e "   brew install allure  # Para Mac"
        echo -e "   # O descargar desde: https://docs.qameta.io/allure/#_installing_a_commandline"
        echo ""
        echo -e "${BLUE}2. Usar Maven plugin (más lento):${NC}"
        echo -e "   mvn allure:serve"
        echo ""
        read -p "¿Quieres usar Maven plugin ahora? (y/n): " -n 1 -r
        echo ""
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            echo -e "${YELLOW}Usando Maven plugin para generar reporte...${NC}"
            mvn allure:serve
        fi
    else
        echo -e "${GREEN}✓ Usando Allure CLI...${NC}"
        echo -e "${YELLOW}Generando reporte HTML...${NC}"
        allure generate target/allure-results --clean -o allure-report
        
        if [ $? -eq 0 ]; then
            echo -e "${GREEN}✓ Reporte generado en: allure-report/${NC}"
            echo -e "${BLUE}Abriendo reporte en el navegador...${NC}"
            allure open allure-report
        else
            echo -e "${RED}✗ Error al generar el reporte${NC}"
            return 1
        fi
    fi
}

# Variables por defecto
TEST_TYPE="all"
BROWSER="default"
HEADLESS="false"
CLEAN_ONLY="false"
REPORT_ONLY="false"

# Procesar argumentos
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_help
            exit 0
            ;;
        -a|--all)
            TEST_TYPE="all"
            shift
            ;;
        -s|--smoke)
            TEST_TYPE="smoke"
            shift
            ;;
        -b|--browser)
            BROWSER="$2"
            shift 2
            ;;
        -e|--headless)
            HEADLESS="true"
            shift
            ;;
        -r|--report)
            REPORT_ONLY="true"
            shift
            ;;
        -c|--clean)
            CLEAN_ONLY="true"
            shift
            ;;
        *)
            echo -e "${RED}Opción desconocida: $1${NC}"
            show_help
            exit 1
            ;;
    esac
done

# Verificar si Java y Maven están instalados
if ! command -v java &> /dev/null; then
    echo -e "${RED}✗ Java no está instalado o no está en el PATH${NC}"
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo -e "${RED}✗ Maven no está instalado o no está en el PATH${NC}"
    exit 1
fi

# Mostrar información del entorno
echo -e "${BLUE}=== Información del Entorno ===${NC}"
echo -e "Java: $(java -version 2>&1 | head -n 1)"
echo -e "Maven: $(mvn -version 2>&1 | head -n 1)"
echo ""

# Ejecutar según las opciones
if [ "$CLEAN_ONLY" = "true" ]; then
    clean_project
    exit 0
fi

if [ "$REPORT_ONLY" = "true" ]; then
    generate_report
    exit 0
fi

# Ejecutar pruebas
run_tests "$TEST_TYPE" "$BROWSER" "$HEADLESS"
test_result=$?

# Generar reporte si las pruebas se ejecutaron
if [ $test_result -eq 0 ]; then
    echo ""
    read -p "¿Deseas generar y abrir el reporte de Allure? (y/n): " -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        generate_report
    fi
fi

exit $test_result 