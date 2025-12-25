# Java Selenide RestAssured Automation Framework

Este proyecto es un framework de automatización completo que combina **Java**, **Selenide** (para UI) y **RestAssured** (para API) siguiendo principios **SOLID** y un patrón **Page Object Model (POM)** modular. Utiliza **Gradle** como herramienta de construcción y gestión de dependencias.

## 🏗️ Arquitectura del Proyecto

```
src/test/java
├── core
│   ├── DriverManager.java              # Gestión del WebDriver
│   ├── BaseTest.java                   # Clase base para tests UI
│   ├── ApiBaseTest.java                # Clase base para tests API
│   └── AllureScreenshotListener.java   # Listener para capturas en Allure
├── pages
│   └── home
│       └── SauceDemoPage.java          # Page Object para SauceDemo
├── steps
│   ├── SauceSteps.java                 # Steps para SauceDemo
│   └── GenericSteps.java               # Steps genéricos reutilizables
├── api
│   ├── client
│   │   └── BookingClient.java          # Cliente API con RestAssured
│   └── models
│       ├── BookingRequest.java         # Modelo para requests
│       └── BookingResponse.java        # Modelo para responses
├── data
│   └── Users.java                      # Datos de prueba
├── utils
│   └── JsonUtils.java                  # Utilidades para JSON
└── tests
    ├── login
    │   └── LoginTest.java              # Tests de login
    ├── home
    │   └── ShortByTest.java            # Tests de ordenamiento
    ├── product
    │   ├── ProductPageTest.java        # Tests de página de producto
    │   └── ProductValidationTest.java  # Tests de validación de producto
    ├── Cart
    │   └── CartStatusTest.java         # Tests del carrito
    └── api
        ├── ApiTest.java                # Tests CRUD de API
        └── ApiSchemaTest.java          # Tests de validación de schema
```

## 🚀 Sitios de Prueba

- **UI Testing**: https://automationintesting.online/
- **API Testing**: https://jsonplaceholder.typicode.com/

## 📋 Prerrequisitos

1. **Java 11 o superior**
2. **Gradle 8.5+** (incluido con Gradle Wrapper)
3. **Google Chrome** (última versión)
4. **ChromeDriver** (se descarga automáticamente)

## 🛠️ Instalación y Configuración

### 1. Instalar Java 11 (OpenJDK)
```bash
# Usando Homebrew (recomendado en macOS)
brew install openjdk@11
```

### 2. Configurar Java en el PATH
```bash
# Agregar Java al PATH y configurar JAVA_HOME
echo 'export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@11"' >> ~/.zshrc
source ~/.zshrc
```

### 3. Instalar Gradle (Opcional - se incluye Gradle Wrapper)
```bash
# Usando Homebrew (opcional, ya que se incluye Gradle Wrapper)
brew install gradle
```

### 4. Verificar instalaciones
```bash
# Verificar Java
java -version

# Verificar Gradle (si está instalado globalmente)
gradle -version

# O usar Gradle Wrapper (recomendado)
./gradlew -version
```

### 5. Construir el proyecto e instalar dependencias
```bash
# Usando Gradle Wrapper (recomendado)
./gradlew build

# O usando Gradle global
gradle build
```

### ⚠️ Solución de Problemas de Instalación

#### Error: "command not found: gradle"
```bash
# Usar Gradle Wrapper en su lugar (recomendado)
./gradlew build

# O instalar Gradle globalmente
brew install gradle
```

#### Error: "Unable to locate a Java Runtime"
```bash
# Java no está instalado
brew install openjdk@11
# Configurar PATH (ver paso 2)
```

#### Error: "JAVA_HOME not set"
```bash
# Configurar JAVA_HOME
export JAVA_HOME="/opt/homebrew/opt/openjdk@11"
echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@11"' >> ~/.zshrc
```

## 🧪 Ejecutar Tests

### Ejecutar todos los tests (descubrimiento automático)
```bash
./gradlew test
```

### Ejecutar usando testng.xml (orden definido)
```bash
./gradlew runSuite
```

### Ejecutar tests por tipo
```bash
./gradlew runUITests        # Solo tests de UI
./gradlew runAPITests       # Solo tests de API
./gradlew runEndToEndTest   # Tests End-to-End
./gradlew runSauceDemoTests # Tests de SauceDemo
```

### Ejecutar test específico
```bash
./gradlew test --tests "tests.login.Login"
./gradlew test --tests "tests.api.*"

# Ejecutar el test
./gradlew test --tests "tests.Cart.CartAddProductTest"
# Ejecutar con reporte Allure (recomendado para ver paso a paso)
./gradlew test --tests "tests.Cart.CartAddProductTest" allureServe
```

### Ejecutar tests en modo headless
```bash
# Modo headless (sin interfaz gráfica) - ideal para CI/CD
./gradlew test -Dselenide.headless=true

# Combinar con tipos específicos
./gradlew runUITests -Dselenide.headless=true
./gradlew runSauceDemoTests -Dselenide.headless=true
```



## 📊 Reportes Allure

### Ejecutar tests con reporte
```bash
./gradlew test allureServe
```

### Con histórico (recomendado para ver tendencias)
```bash
./gradlew testWithHistory allureServe
```

> **Nota**: No uses `clean` si quieres mantener el histórico de ejecuciones.

### Solo generar/ver reporte (sin ejecutar tests)
```bash
./gradlew allureReport              # Genera reporte
./gradlew allureServe               # Genera y abre en navegador
allure serve build/allure-results   # Usando CLI de Allure
```

### Reporte HTML básico de TestNG
```bash

open build/reports/tests/test/index.html
```

### Ver logs
- **Consola**: Output directo durante la ejecución
- **Reportes Allure**: `build/reports/allure-report/allureReport/index.html`
- **Reportes TestNG**: `build/reports/tests/test/index.html`

## � CI/CD - Jenkins Pipeline

Este proyecto incluye un pipeline de Jenkins configurado para ejecución automática de tests.

### Configuración del Pipeline

El pipeline está definido en `Jenkinsfile` y ejecuta las siguientes etapas:

1. **Checkout**: Clona el repositorio desde GitHub
2. **Build**: Compila el código de tests (`./gradlew clean compileTestJava`)
3. **Run Tests**: Ejecuta todos los tests en modo headless
4. **Reports**: Genera reportes Allure, TestNG y JUnit

### Variables de Entorno

```groovy
JAVA_HOME = '/opt/homebrew/Cellar/openjdk@11/11.0.29/libexec/openjdk.jdk/Contents/Home'
PATH = "${JAVA_HOME}/bin:${env.PATH}"
GRADLE_OPTS = '-Dorg.gradle.daemon=false'
```

### Ejecución en Jenkins

Los tests se ejecutan automáticamente en **modo headless** (sin interfaz gráfica):

```bash
./gradlew test -Dselenide.headless=true
```

**Ventajas del modo headless:**
- ✅ Más rápido (sin renderizado de UI)
- ✅ Menor consumo de recursos
- ✅ Ideal para servidores CI/CD sin display
- ✅ Ejecución en paralelo sin conflictos

### Reportes Generados

El pipeline genera automáticamente:

- **Allure Report**: Reportes interactivos con pasos detallados
- **TestNG Report**: Reporte HTML básico en `build/reports/tests/test/index.html`
- **JUnit XML**: Resultados en formato XML para integración con Jenkins

### Configuración Local vs Jenkins

| Aspecto | Local | Jenkins |
|---------|-------|---------|
| **Modo** | Normal (con UI) | Headless |
| **ChromeDriver** | Auto-descarga | Auto-descarga |
| **Reportes** | Manual (`allureServe`) | Automático |
| **Limpieza** | Manual | Automática (`cleanWs()`) |

### Trigger del Pipeline

El pipeline se puede ejecutar:
- **Manual**: Desde la interfaz de Jenkins
- **Automático**: Configurando webhooks en GitHub (push/PR)
- **Programado**: Usando cron syntax en Jenkins

## �� Configuración Personalizada

### Modificar configuración del navegador
Edita `src/test/java/core/DriverManager.java`:
```java
// Para modo headless
options.addArguments("--headless");

// Para cambiar resolución
Configuration.browserSize = "1366x768";
```

### Modificar URLs de prueba
Edita `src/test/java/core/BaseTest.java`:
```java
protected static final String UI_BASE_URL = "tu-url-ui";
protected static final String API_BASE_URL = "tu-url-api";
```

## 🧩 Principios SOLID Aplicados

1. **Single Responsibility**: Cada clase tiene una responsabilidad específica
2. **Open/Closed**: Fácil extensión sin modificar código existente
3. **Liskov Substitution**: Las clases derivadas son substituibles
4. **Interface Segregation**: Interfaces específicas y cohesivas
5. **Dependency Inversion**: Dependencias hacia abstracciones

## 📝 Estructura de Tests

### Test End-to-End Incluye:
1. **UI Navigation**: Navegación por el sistema de booking
3. **API Operations**: Operaciones CRUD en la API
4. **Complete Workflow**: Flujo completo UI + API

### Datos de Prueba:
- **Admin**: `admin / password`
- **Test User**: Datos generados dinámicamente
- **API Data**: Posts de prueba en JSONPlaceholder


### Error: ChromeDriver not found
```bash
# Verificar que Chrome esté instalado
google-chrome --version

# El ChromeDriver se descarga automáticamente
# Si hay problemas, verificar la versión de Chrome
```

### Error: Tests fallan por timeouts
```bash
# Aumentar timeouts en DriverManager.java
Configuration.timeout = 15000;
Configuration.pageLoadTimeout = 45000;
```

### Error: Gradle build falla
```bash
# Limpiar y reconstruir
./gradlew clean build

# Ver más detalles del error
./gradlew build --stacktrace
```

### Error: API tests fallan
```bash
# Verificar conectividad
curl https://jsonplaceholder.typicode.com/posts/1
```

## 📈 Extensión del Framework

### Agregar nueva página:
1. Crear clase en `pages/nueva-seccion/NuevaPagina.java`
2. Implementar Page Object pattern
3. Crear Steps correspondiente en `steps/`

### Agregar nueva API:
1. Crear modelo en `api/models/`
2. Extender o crear nuevo cliente en `api/client/`
3. Agregar tests en `tests/`

### Agregar nuevos datos:
1. Extender `data/Users.java`
2. Crear nuevas clases de datos según necesidad

## 🤝 Contribución

1. Seguir principios SOLID
2. Mantener cobertura de tests
3. Documentar cambios importantes
4. Usar logging apropiado


