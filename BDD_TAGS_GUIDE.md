# Guía de Tags BDD para Jenkins

## 📌 Tags disponibles

### Tags de Feature (nivel de funcionalidad)
```gherkin
@checkout-flow    # Feature completo de checkout
@login-flow       # Feature de login (cuando lo crees)
@cart-flow        # Feature de carrito (cuando lo crees)
```

### Tags de Severidad (nivel de escenario)
```gherkin
@critical         # Tests críticos
@normal           # Tests normales
@low              # Tests de baja prioridad
```

### Tags generales
```gherkin
@bdd              # Todos los tests BDD
@all              # Incluido en todos los grupos
```

## 🎯 Cómo usar tags en Jenkins

### Opción 1: Checkbox BDD (Configurado)

En Jenkins, ahora tienes un checkbox **RUN_BDD** que ejecuta todos los tests BDD:

```
☐ RUN_ALL
☐ RUN_CRITICAL
☐ RUN_NORMAL
☐ RUN_API
☐ RUN_LOW
✓ RUN_BDD          ← Nuevo checkbox
☐ HEADLESS
```

**Ejemplo de uso:**
- Marca solo **RUN_BDD** → Ejecuta todos los tests BDD
- Marca **RUN_CRITICAL + RUN_BDD** → Ejecuta tests críticos tradicionales + tests BDD

## 📝 Convenciones de nombres de tags

### Recomendaciones:
- **Usa kebab-case**: `@checkout-flow` en lugar de `@checkoutFlow`
- **Sé específico**: `@checkout-flow` en lugar de solo `@checkout`
- **Agrupa por funcionalidad**: `@login-flow`, `@cart-flow`, `@checkout-flow`
- **Usa tags de severidad**: `@critical`, `@normal`, `@low`
- **Usa tags de tipo**: `@smoke`, `@regression`, `@e2e`

### Tags comunes:
```gherkin
# Por funcionalidad
@login-flow
@checkout-flow
@cart-flow
@product-flow

# Por severidad
@critical
@normal
@low
@blocker

# Por tipo de test
@smoke
@regression
@e2e
@integration

# Por estado
@wip          # Work in progress
@skip         # Temporalmente deshabilitado
@bug-123      # Relacionado con bug específico
```

## 🚀 Configuración avanzada en Jenkins

### Añadir dropdown para tags específicos

Si quieres un dropdown en Jenkins para seleccionar features específicos:

```groovy
choice(
    name: 'BDD_FEATURE',
    choices: ['all', 'checkout-flow', 'login-flow', 'cart-flow'],
    description: 'Select specific BDD feature to run'
)
```

Y en el stage de tests:

```groovy
if (params.RUN_BDD) {
    if (params.BDD_FEATURE != 'all') {
        sh "./gradlew runBDD -Dcucumber.filter.tags='@${params.BDD_FEATURE}'"
    } else {
        severityGroups.add('bdd')
    }
}
```

## 📊 Ejemplos de combinaciones

### Ejecutar solo checkout crítico:
```bash
./gradlew runBDD -Dcucumber.filter.tags="@checkout-flow and @critical"
```

### Ejecutar todos los smoke tests BDD:
```bash
./gradlew runBDD -Dcucumber.filter.tags="@bdd and @smoke"
```

### Ejecutar todo excepto WIP:
```bash
./gradlew runBDD -Dcucumber.filter.tags="@bdd and not @wip"
```

### Ejecutar múltiples features:
```bash
./gradlew runBDD -Dcucumber.filter.tags="@checkout-flow or @login-flow"
```



## 📚 Recursos

- [Cucumber Tag Expressions](https://cucumber.io/docs/cucumber/api/#tag-expressions)
- [Gherkin Best Practices](https://cucumber.io/docs/gherkin/reference/)
- [BDD Anti-patterns](https://cucumber.io/docs/bdd/better-gherkin/)
