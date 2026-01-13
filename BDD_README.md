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

## 📝 Ejemplo de Feature (Gherkin)

```gherkin
@checkout @bdd
Feature: Checkout Flow
  As a user of SauceDemo
  I want to complete a purchase
  So that I can buy products

  Background:
    Given I am on the SauceDemo homepage
    And I login with valid credentials

  @critical @all
  Scenario: Complete checkout process successfully
    Given I am on the products page
    When I add a random product to cart
    And I navigate to the cart
    Then the cart badge should match the product count
    When I proceed to checkout
    And I fill the checkout form with:
      | firstName | lastName  | zipCode |
      | Quality   | Assurance | 12345   |
    And I continue to the overview page
    Then I should see payment information as "SauceCard"
    And I should see shipping information as "Free"
    And the total calculation should be correct
    When I finish the checkout
    Then I should see the success message "Thank you for your order"
    And I should be on the complete page

  @normal @all
  Scenario Outline: Complete checkout with different user data
    Given I am on the products page
    When I add a random product to cart
    And I proceed to checkout
    And I fill the checkout form with:
      | firstName   | lastName   | zipCode   |
      | <firstName> | <lastName> | <zipCode> |
    And I continue to the overview page
    Then I should see payment information as "SauceCard"
    When I finish the checkout
    Then I should see the success message "Thank you for your order"

    Examples:
      | firstName | lastName | zipCode |
      | John      | Doe      | 10001   |
      | Jane      | Smith    | 90210   |
      | Bob       | Johnson  | 60601   |
```

## 🔧 Cómo crear nuevos tests BDD

### 1. Crear un nuevo Feature file

Crea un archivo `.feature` en `src/test/resources/features/`:

```gherkin
@login @bdd
Feature: User Login

  @critical @all
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be on the products page
```

### 2. Crear Step Definitions

Crea o actualiza archivos en `src/test/java/stepdefinitions/`:

```java
package stepdefinitions;

import io.cucumber.java.en.*;
import steps.SauceSteps;

public class LoginSteps {

    private final SauceSteps sauceSteps = new SauceSteps();

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        open("https://www.saucedemo.com/");
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        $(Login.userName).setValue(username);
    }

    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        $(Login.password).setValue(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        $(Login.loginCta).click();
    }

    @Then("I should be on the products page")
    public void iShouldBeOnTheProductsPage() {
        $(Home.productList).shouldBe(visible);
    }
}
```

### 3. Reutilizar Steps existentes

Los step definitions pueden usar tus clases `SauceSteps` y `GenericSteps` existentes:

```java
@When("I add a random product to cart")
public void iAddARandomProductToCart() {
    genericSteps.clickRandomElement(Product.productTitle);
    genericSteps.shouldBeVisible(Product.productPrice);
    genericSteps.clickElement(Home.addToCartButton);
}
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

### JSON Report

Para integración con otras herramientas:

```
build/reports/cucumber/cucumber.json
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
