# 📦 Maven

---

## 📌 Table of Contents

- [Why Maven?](#why-maven)
- [Where it fits](#where-it-fits)
- [Core Concepts](#core-concepts)
  - [JAR Files](#jar-files)
  - [Standard Project Structure](#standard-project-structure)
  - [pom.xml](#pomxml)
  - [Dependency Management](#dependency-management)
  - [Repositories](#repositories)
  - [Maven Lifecycle](#maven-lifecycle)
  - [Archetypes](#archetypes)
- [Mental Model](#mental-model)

---

## Why Maven?

Without a build tool, managing a real Java project means:

- Manually downloading `.jar` files for every library
- Adding them to the classpath by hand
- Tracking and downloading transitive dependencies (dependencies of dependencies)
- Repeating this for every teammate's machine
- Manually remembering the correct sequence of commands to compile, test, and package

This falls apart completely on a real project with 50+ dependencies and multiple developers. Maven automates all of it — you declare what you want in a config file, Maven figures out how to get it done.

---

## Where it Fits

Maven sits between your source code and a deployable application:

```
Your source code  →  Maven (build tool)  →  Compiled classes / JAR / WAR
                           ↑
                 Fetches dependencies automatically
                     from repositories
```

It has nothing to do with your application logic — it is infrastructure that manages how your project is built, what libraries it uses, and how it is packaged.

> This is why Maven is the first thing to learn before Spring. Spring itself is just a collection of JAR files — Maven is what pulls them in.

---

## Core Concepts

### JAR Files

A JAR is a zipped bundle of compiled `.class` files plus metadata. It is the packaged output of a Java project. Maven's job is not to define the JAR format — it is to manage JARs at scale: hundreds of them, across versions, without conflicts.

---

### Standard Project Structure

Maven enforces a standard layout for every project:

```
project/
├── src/
│   ├── main/
│   │   ├── java/           ← application source code
│   │   └── resources/      ← config files, properties
│   └── test/
│       ├── java/           ← test source code
│       └── resources/
└── pom.xml
```

Because every Maven project looks the same, any Java developer can open any Maven project and immediately know where things live — no onboarding needed.

---

### `pom.xml`

The heart of a Maven project. Think of it as the project's contract:

- **Identity** — `groupId`, `artifactId`, `version`
- **Dependencies** — what libraries the project needs
- **Build config** — how the project should be built

You declare the desired state; Maven executes it.

---

### Dependency Management

Maven's most important feature. You declare one dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Maven doesn't just fetch that one JAR — it resolves the entire **transitive dependency tree**. `spring-boot-starter-web` alone pulls in Tomcat, Jackson, Spring MVC, and more. You never touch any of that manually.

---

### Repositories

Two layers, working like a cache hierarchy:

| Repository | Location | Purpose |
|---|---|---|
| **Local** | `~/.m2` on your machine | Personal cache — once downloaded, reused across all projects |
| **Central** | Public internet | Source Maven pulls from if the library is not already cached locally |

> This is why your second Maven project builds faster than your first — most dependencies are already in the local cache.

---

### Maven Lifecycle

Maven automates the steps you would otherwise run manually:

| Lifecycle | Description |
|---|---|
| **clean** | Wipes previous build output |
| **default** | The main build pipeline — `validate → compile → test → package → install → deploy` |
| **site** | Generates project documentation (rarely used day-to-day) |

Running `mvn clean package` executes the full pipeline in order — no need to manually invoke `javac`, run the test runner, then zip into a JAR. One command, all steps, always in the correct order.

---

### Archetypes

Project templates that generate a standard Maven project skeleton (e.g. a standard web app structure). Less commonly used once you start using Spring Initializr for Spring Boot projects, but useful to know as a general Maven concept.

---

## Mental Model

Maven answers three questions for any Java project:

| Question | Answer |
|---|---|
| **What does my project need?** | Dependencies — declared in `pom.xml` |
| **Where does it come from?** | Repositories — local cache first, then central |
| **How do I turn source into something runnable?** | Lifecycle — compile, test, package |