# Java Selenide RestAssured Automation Framework

Este proyecto es un framework de automatización completo que combina **Java**, **Selenide** (para UI) y **RestAssured** (para API) siguiendo principios **SOLID** y un patrón **Page Object Model (POM)** modular. Utiliza **Gradle** como herramienta de construcción y gestión de dependencias.

## 🏗️ Arquitectura del Proyecto

```
src/test/java
├── core
│   ├── DriverManager.java      # Gestión del WebDriver
│   └── BaseTest.java          # Clase base para todos los tests
├── pages
│   ├── home
│   │   └── HomePage.java      # Page Object para la página principal
│   └── login
│       └── LoginPage.java     # Page Object para login
├── steps
│   ├── LoginSteps.java        # Steps para funcionalidad de login
│   └── BookingSteps.java      # Steps para funcionalidad de booking
├── api
│   ├── client
│   │   └── BookingClient.java # Cliente API con RestAssured
│   └── models
│       ├── BookingRequest.java # Modelo para requests
│       └── BookingResponse.java # Modelo para responses
├── data
│   └── Users.java             # Datos de prueba
├── utils
│   └── JsonUtils.java         # Utilidades para JSON
└── tests
    └── EndToEndTest.java      # Tests End-to-End
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

### 5. Clonar/Descargar el proyecto
```bash
# Si usas Git
git clone <tu-repo>
cd JavaSelenide

# O simplemente navega al directorio del proyecto
cd /Users/javiervt/Desktop/JavaSelenide
```

### 6. Construir el proyecto e instalar dependencias
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

### Ejecutar todos los tests
```bash
# Usando Gradle Wrapper (recomendado)
./gradlew test

# O usando Gradle global
gradle test
```

### Ejecutar tests específicos
```bash
# Solo tests de UI
./gradlew runUITests

# Solo tests de API
./gradlew runAPITests

# Test completo End-to-End
./gradlew runEndToEndTest

# Ejecutar test específico por clase
./gradlew test --tests "tests.EndToEndTest"

# Ejecutar método específico
./gradlew test --tests "tests.EndToEndTest.testCompleteEndToEndWorkflow"
```

### Ejecutar con TestNG XML (configuración por defecto)
```bash
./gradlew test
```

## 📊 Reportes

### Generar reporte Allure
```bash
# Instalar Allure (si no está instalado)
npm install -g allure-commandline

# Generar y abrir reporte con Gradle
./gradlew test allureReport
allure serve build/allure-results

# O usar el plugin de Allure para Gradle
./gradlew allureServe
```

### Ver logs
Los logs se generan en:
- **Consola**: Output directo durante la ejecución
- **Archivo**: `build/logs/automation.log`
- **Reportes de tests**: `build/reports/tests/test/index.html`

## 🔧 Configuración Personalizada

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
2. **Admin Login**: Funcionalidad de login de administrador
3. **API Operations**: Operaciones CRUD en la API
4. **Complete Workflow**: Flujo completo UI + API

### Datos de Prueba:
- **Admin**: `admin / password`
- **Test User**: Datos generados dinámicamente
- **API Data**: Posts de prueba en JSONPlaceholder

## 🐛 Troubleshooting

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

## 📄 Licencia

Este proyecto es para propósitos educativos y de demostración.
