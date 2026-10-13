# ☕ Backend Java Technology

---

## 📌 Table of Contents

- [Evolution of Backend Java](#evolution-of-backend-java)
- [Library vs Framework](#library-vs-framework)
- [Roadmap](#roadmap)

---

## Evolution of Backend Java

### 1.1 Servlets & JSPs — Late 1990s

**Core mechanisms:** Manual URL routing, raw HTTP request/response parsing, manual JDBC database connections, and HTML mixed directly into Java code.

**Main drawback:** High boilerplate, tight coupling between business logic and infrastructure, and no separation of concerns.

---

### 1.2 Enterprise JavaBeans (EJB 2.x)

**Core mechanisms:** Introduced by Sun Microsystems to standardise transactions, distributed objects, and security in enterprise applications.

**Main drawback:** Extremely heavyweight architecture requiring multiple interface files per business method. Caused *"XML Hell"* — massive, fragile configuration files (`applicationContext.xml`, `web.xml`) that failed at runtime rather than compile-time.

---

### 1.3 Early Spring Framework — 2002

**Core mechanisms:** Introduced Dependency Injection (DI) and Inversion of Control (IoC) using lightweight POJOs to manage object lifecycles automatically. Eliminated EJB architectural complexity.

**Main drawback:** Setting up projects still took days due to extensive manual framework configuration, deployment setup, and web server management.

---

### 1.4 Spring Boot — 2014 to Present

Created to eliminate *"Spring Configuration Fatigue"* and support modern microservice architectures through three primary innovations:

| Innovation | Description |
|---|---|
| **Embedded Servers** | Packages applications into self-contained executable `.jar` files with a built-in server (e.g. Tomcat) — no need to deploy `.war` files to a standalone web server |
| **Auto-Configuration** | Automatically sets up application components (e.g. in-memory databases, web routers) by analysing classpath dependencies |
| **Opinionated Starters** | Replaces manual dependency and version management with single curated packages (e.g. `spring-boot-starter-web`, `spring-boot-starter-data-jpa`) |

---

## Library vs Framework

| | Control | Description |
|---|---|---|
| **Library** | Stays with you | A set of tools you call directly from your code to perform specific tasks |
| **Framework** | Inverted to the framework | An overarching structure that runs the main program and calls your custom code at specific points |

---

## Roadmap

| # | Topic | Notes |
|---|---|---|
| 1 | **Maven** | Build tool — dependency management, project structure, lifecycle |
| 2 | **Spring Core** | IoC container, Dependency Injection, bean lifecycle |
| 3 | **Spring Boot** | Auto-configuration, embedded servers, starters |
| ... | *More to be added* | |