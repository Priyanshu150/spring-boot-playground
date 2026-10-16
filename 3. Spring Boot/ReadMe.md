# 🚀 Spring Boot

---

## 📌 Table of Contents

- [Starters & Parent POM](#starters--parent-pom)
- [@SpringBootApplication](#springbootapplication)
- [Auto-Configuration Internals](#auto-configuration-internals)
- [Configuration Files](#configuration-files)
- [Value Injection](#value-injection)
- [@ConfigurationProperties](#configurationproperties)
- [ApplicationRunner & CommandLineRunner](#applicationrunner--commandlinerunner)
- [Running a Spring Boot Application](#running-a-spring-boot-application)

---

## Starters & Parent POM

### `spring-boot-starter-parent`

A parent POM that provides default configurations for a Spring Boot project — dependency version management, plugin configurations, and sensible build defaults. You inherit it in `pom.xml` to avoid managing versions manually.

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.x.x</version>
</parent>
```

### `spring-boot-starter-*` Dependencies

Curated dependency bundles. Instead of adding individual libraries and managing their versions, you add one starter and get everything needed for that capability.

| Starter | What it provides |
|---|---|
| `spring-boot-starter-web` | Spring MVC, Tomcat, Jackson |
| `spring-boot-starter-data-jpa` | Hibernate, Spring Data JPA |
| `spring-boot-starter-test` | JUnit, Mockito, AssertJ |

---

## `@SpringBootApplication`

The entry-point annotation for every Spring Boot application. It is a composed annotation that combines three annotations:

```
@SpringBootApplication
    │
    ├── @SpringBootConfiguration
    ├── @EnableAutoConfiguration
    └── @ComponentScan
```

| Annotation | Description |
|---|---|
| `@SpringBootConfiguration` | Marks the class as a configuration source — same as `@Configuration` in Spring Core |
| `@EnableAutoConfiguration` | Triggers automatic bean configuration based on the dependencies present on the classpath |
| `@ComponentScan` | Instructs Spring to scan the current package and all sub-packages for components |

---

## Auto-Configuration Internals

Spring Boot's auto-configuration creates beans conditionally — only when certain conditions are met. This is driven by two key annotations used internally:

| Annotation | Behaviour |
|---|---|
| `@AutoConfiguration` | Marks a class as an auto-configuration candidate — Spring creates beans from it for the IoC container |
| `@ConditionalOnClass` | Creates the bean only if a specific dependency/class is present on the classpath |
| `@ConditionalOnMissingBean` | Creates the bean only if no other bean of that type has already been registered |

This is how Spring Boot avoids overriding beans you define yourself — if you register your own bean, the auto-configured one is skipped.

---

## Configuration Files

Spring Boot supports multiple ways to provide configuration, resolved in the following priority order (higher overrides lower):

| Priority | Source | Example |
|---|---|---|
| 1 (highest) | Command-line arguments | `--server.port=9090` |
| 2 | Environment variables | `SERVER_PORT=9090` |
| 3 | System properties | `-Dserver.port=9090` |
| 4 | YAML file | `application.yml` |
| 5 (lowest) | Properties file | `application.properties` |

### `application.properties` vs `application.yml`

Both do the same job — they are non-Java configuration files used to externalise application settings.

```properties
# application.properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
```

```yaml
# application.yml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
```

---

## Value Injection

Injecting values from `application.properties` into your beans using `@Value`:

### Field Injection

```java
@Value("${app.name}")
private String appName;
```

### Constructor Injection

```java
public MyService(@Value("${app.name}") String appName) {
    this.appName = appName;
}
```

### Setter Injection

```java
@Value("${app.name}")
public void setAppName(String appName) {
    this.appName = appName;
}
```

### Default Value

If the property is not found, a default can be provided:

```java
@Value("${app.timeout:30}")   // defaults to 30 if not set
private int timeout;
```

---

## `@ConfigurationProperties`

Maps an entire group of properties to a class rather than injecting them one by one with `@Value`. Cleaner and type-safe for related configuration.

```properties
# application.properties
app.name=MyApp
app.timeout=30
app.max-connections=100
```

```java
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String name;
    private int timeout;
    private int maxConnections;
}
```

> Prefer `@ConfigurationProperties` over `@Value` when you have a group of related properties.

---

## ApplicationRunner & CommandLineRunner

Both are interfaces that let you run code automatically **after the Spring context has started** — useful for setup tasks, preloading data, or startup checks.

| Interface | Method | Argument type |
|---|---|---|
| `ApplicationRunner` | `run(ApplicationArguments args)` | Parsed named/option arguments |
| `CommandLineRunner` | `run(String... args)` | Raw command-line string arguments |

```java
@Component
public class StartupRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Application started — running startup logic");
    }
}
```

> Use `ApplicationRunner` when you need to work with named arguments. Use `CommandLineRunner` for simple string-based args.

---

## Running a Spring Boot Application

```bash
mvn spring-boot:run
```

This compiles, resolves dependencies, and starts the embedded server in one command.