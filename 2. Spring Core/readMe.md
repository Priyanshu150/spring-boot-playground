# 🌱 Spring Core Framework

---

## 📌 Table of Contents

- [The Problem — Tight Coupling](#the-problem--tight-coupling)
- [IoC and DI](#ioc-and-di)
- [Bean Configuration Approaches](#bean-configuration-approaches)
- [Spring Framework & IoC Container](#spring-framework--ioc-container)
- [Spring vs Manual Handling](#spring-vs-manual-handling)
- [Key Annotations](#key-annotations)
- [Ways to Create Beans](#ways-to-create-beans)
- [Constructor vs Field vs Setter Injection](#constructor-vs-field-vs-setter-injection)
- [Bean Scope](#bean-scope)
- [Bean Lifecycle](#bean-lifecycle)

---

## The Problem — Tight Coupling

When one class creates an instance of its dependency directly, the two become tightly coupled. A change in the dependency affects the class that owns it.

```
OrderService
    └── new PaymentService()    ← tightly coupled; OrderService controls creation
```

---

## IoC and DI

| | Type | Description |
|---|---|---|
| **IoC** (Inversion of Control) | Principle | The external framework controls the main flow and calls your custom code when needed — you give up control of object creation and lifecycle |
| **DI** (Dependency Injection) | Design pattern | A specific way to implement IoC — instead of a class creating its own dependencies, they are provided from outside |

```
Without DI:   OrderService creates PaymentService         ← class controls its dependency
With DI:      PaymentService is injected into OrderService ← framework controls it
```

---

## Bean Configuration Approaches

There are two ways to tell Spring how to manage beans:

| Approach | Description |
|---|---|
| **XML-based configuration** | Bean definitions are declared in XML files (e.g. `applicationContext.xml`). Used in legacy systems — verbose and error-prone since mistakes fail at runtime, not compile-time |
| **Annotation-based configuration** | Bean definitions are declared using annotations directly in the code (e.g. `@Component`, `@Bean`, `@Configuration`). The modern standard — less boilerplate and errors are caught earlier |

---

## Spring Framework & IoC Container

Spring is an external framework that manages the IoC principle in your code through its **IoC Container**.

The IoC Container is responsible for:

1. **Creating objects** (beans)
2. **Managing objects** throughout their lifecycle
3. **Connecting objects** together by injecting dependencies

---

## Spring vs Manual Handling

| Problem | Without Spring | With Spring |
|---|---|---|
| **Boilerplate "new-ing" up** | Every dependency created manually with `new` | Spring creates and wires beans automatically |
| **Lifecycle complexity** | You manage init, cleanup, and destruction | Spring handles lifecycle callbacks |
| **Tight coupling to infrastructure** | Business logic tied to concrete implementations | Business logic depends on abstractions; Spring injects the right implementation |

---

## Key Annotations

| Annotation | Purpose |
|---|---|
| `@Configuration` | Marks a class as a source of bean definitions |
| `@ComponentScan("package")` | Tells Spring which packages to scan for components |
| `@Bean` | Declares a method whose return value is registered as a bean in the IoC container |
| `@Component` | Marks a class so Spring auto-detects and registers it as a bean |
| `@Autowired` | Tells Spring to inject a dependency automatically |
| `@Qualifier("name")` | Used alongside `@Autowired` when multiple beans of the same type exist — specifies which one to inject |

---

## Ways to Create Beans

| Approach | Annotation | When to use |
|---|---|---|
| **Component scanning** | `@Component` | Mark a class directly; Spring detects and registers it automatically |
| **Bean method** | `@Bean` inside a `@Configuration` class | When you need to control how the object is created, or when the class is from a third-party library you cannot annotate |

---

## Constructor vs Field vs Setter Injection

| | Constructor Injection | Field Injection | Setter Injection |
|---|---|---|---|
| **How** | Dependency passed via constructor | `@Autowired` directly on field | `@Autowired` on setter method |
| **Recommended** | ✅ Yes — preferred | ❌ Avoid — harder to test | ⚠️ Only for optional dependencies |
| **Immutability** | ✅ Field can be `final` | ❌ Cannot be `final` | ❌ Cannot be `final` |
| **Testability** | ✅ Easy to pass mock in tests | ❌ Requires reflection to inject | ⚠️ Possible but not clean |

---

## Bean Scope

| Scope | Initialisation | State | Behaviour |
|---|---|---|---|
| **Singleton** | Eager (at container startup) | Stateless | Only **one** instance per bean definition exists in the IoC container |
| **Prototype** | Lazy (on each request) | Stateful | A **new** instance is created every time the bean is requested |

---

## Bean Lifecycle

### Singleton (Eager Initialisation)

```
IoC container starts
        ↓
Read configuration (annotations / XML)
        ↓
Read bean definitions
        ↓
Instantiate objects
        ↓
Inject dependencies
        ↓
Aware interfaces called       ← callback methods implemented by bean
        ↓
Initialisation callbacks
    ├── InitializingBean.afterPropertiesSet()
    ├── init-method
    └── @PostConstruct
        ↓
Bean is ready to use
        ↓
Destruction callbacks (on container shutdown)
    ├── DisposableBean.destroy()
    ├── destroy-method
    └── @PreDestroy
        ↓
Bean destroyed
```

---

### Prototype (Lazy Initialisation)

Prototype follows the same steps up to ready-to-use, but starts only when the bean is explicitly requested — not at container startup.

```
IoC container starts
        ↓
Read configuration (annotations / XML)
        ↓
Read bean definitions
        ↓
        ·  ·  ·  (waits until bean is requested)
        ↓
Instantiate object
        ↓
Inject dependencies
        ↓
Aware interfaces called
        ↓
Initialisation callbacks
        ↓
Bean handed to client
        ↓
Spring does NOT manage destruction  ← client is responsible
```

**Why doesn't Spring handle prototype bean destruction?**

Spring hands the prototype bean to the client and forgets about it. If Spring tracked every prototype instance, the references would accumulate in memory indefinitely — causing a memory leak. The client is responsible for cleanup.