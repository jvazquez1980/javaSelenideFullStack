# Cucumber BDD Testing Guide

## 📋 Resumen

Este proyecto ahora soporta **BDD (Behavior-Driven Development)** usando **Cucumber** con sintaxis **Gherkin**, además de los tests tradicionales de TestNG.


## 📁 Estructura de archivos BDD

```
src/test/
├── java/
│   ├── runners/
│   │   └── CucumberTestRunner.java      # Runner TestNG para Cucumber
│   ├── stepdefinitions/
│   │   └── CheckoutSteps.java           # Step definitions (Given/When/Then)
│   └── hooks/
│       └── CucumberHooks.java           # Setup/Teardown para cada escenario
└── resources/
    └── features/
        └── Checkout.feature             # Escenarios en Gherkin
```

## 🚀 Cómo ejecutar tests BDD

### Localmente

```bash
# Solo tests BDD
./gradlew runBDD

# Tests BDD en modo headless
./gradlew runBDD -Dselenide.headless=true

# Usando el sistema de grupos (alternativa)
./gradlew runBySeverity -Pseverity=bdd
```

### En Jenkins

**Opción 1: Añadir checkbox BDD** (recomendado)

Edita el `Jenkinsfile` y añade:

```groovy
booleanParam(
    name: 'RUN_BDD',
    defaultValue: false,
    description: '✓ Run BDD tests'
)
```

Y en el stage de tests:

```groovy
if (params.RUN_BDD) severityGroups.add('bdd')
```

**Opción 2: Ejecutar directamente**

Los tests BDD se ejecutan automáticamente cuando seleccionas "all" en Jenkins, ya que tienen el tag `@all`.

### 2. Crear Step Definitions

Crea o actualiza archivos en `src/test/java/stepdefinitions/`:
```

## 📊 Reportes

### Cucumber HTML Report

Después de ejecutar los tests BDD, el reporte HTML se genera en:

```
build/reports/cucumber/cucumber-report.html
```

### Allure Report

Los tests BDD también aparecen en los reportes de Allure:

```bash
./gradlew allureServe
```

## 🏷️ Tags en Cucumber

Los tags permiten filtrar qué escenarios ejecutar:

```gherkin
@checkout @bdd @critical
Scenario: Complete checkout
  # Este escenario tiene 3 tags
```

Ejecutar por tags:

```bash
# Solo escenarios con @critical
./gradlew runBDD -Dcucumber.filter.tags="@critical"

# Escenarios con @checkout pero sin @slow
./gradlew runBDD -Dcucumber.filter.tags="@checkout and not @slow"
```

## 🔄 Convivencia con tests tradicionales

Los tests BDD y los tests tradicionales de TestNG **coexisten sin problemas**:

- **Tests tradicionales**: `src/test/java/tests/`
- **Tests BDD**: `src/test/resources/features/`

Puedes ejecutar:
- Solo tests tradicionales: `./gradlew test`
- Solo tests BDD: `./gradlew runBDD`
- Todos los tests: `./gradlew runBySeverity -Pseverity=all`

## 🎓 Ventajas de BDD

1. **Legibilidad**: Los escenarios están en lenguaje natural
2. **Colaboración**: Product Owners y QA pueden escribir escenarios
3. **Documentación viva**: Los features documentan el comportamiento del sistema
4. **Reutilización**: Los step definitions se reutilizan entre escenarios
5. **Data-driven**: Los `Scenario Outline` permiten ejecutar el mismo escenario con diferentes datos

## 📚 Recursos adicionales

- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Gherkin Syntax](https://cucumber.io/docs/gherkin/reference/)
- [Cucumber with TestNG](https://cucumber.io/docs/cucumber/api/#testng)
- [Allure Cucumber Integration](https://docs.qameta.io/allure/#_cucumber_jvm)

## ❓ FAQ

**P: ¿Puedo mezclar tests BDD y tradicionales en el mismo proyecto?**
R: Sí, ambos tipos de tests coexisten perfectamente.

**P: ¿Los tests BDD aparecen en los reportes de Allure?**
R: Sí, Allure soporta Cucumber nativamente.

**P: ¿Necesito cambiar mi Jenkinsfile?**
R: No, Jenkins ejecuta los tests BDD automáticamente. Opcionalmente puedes añadir un checkbox para ejecutar solo BDD.

**P: ¿Puedo usar mis Steps y Page Objects existentes?**
R: Sí, los step definitions de Cucumber pueden usar todas tus clases existentes.

**P: ¿Cómo ejecuto solo un escenario específico?**
R: Usa tags: `./gradlew runBDD -Dcucumber.filter.tags="@nombre_del_tag"`
