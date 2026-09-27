# 20-Day Backend Developer Roadmap (Java → Spring Boot)

**Assumption:** You know Core Java (OOP, Collections, Exceptions, Streams, File I/O) but not multithreading yet. That's fine — most CRUD backend work doesn't need deep multithreading on day 1. You'll pick it up naturally once you hit async processing later.

**Philosophy for these 20 days:** Every day = concept + why it exists in real projects + hands-on code. Don't just read — type the code yourself, break it, fix it. By Day 20 you'll have one deployable project you can show in an interview.

---

## Phase 1: Build Tools & Core Spring Foundations (Days 1–5)

### Day 1 — Maven Basics
**Why it matters:** Every real Java project needs a way to manage dependencies and build lifecycle. You can't `import` a library that isn't downloaded — Maven does that.
- Learn: `pom.xml` structure, dependencies, plugins, repositories
- Learn: Maven lifecycle — `clean`, `compile`, `test`, `package`, `install`
- **Hands-on:** Create a plain Maven project (no Spring yet). Add a dependency (e.g., `commons-lang3`), write a small utility class using it, run `mvn clean package` and inspect the generated JAR.

### Day 2 — Spring Core: IoC & Dependency Injection
**Why it matters:** This is the *why* behind Spring itself — instead of you creating objects with `new`, Spring creates and wires them for you. This is what makes large codebases testable and loosely coupled.
- Learn: IoC container, Beans, `@Component`, `@Autowired`, ApplicationContext
- Learn: Constructor injection vs field injection (and why constructor injection is preferred)
- **Hands-on:** Plain Spring (no Boot) project. Create 2-3 classes (e.g., `PaymentService`, `NotificationService`) and wire them via `@Component`/`@Autowired`. Print objects to prove Spring is managing them, not you.

### Day 3 — Spring Bean Scopes & Configuration
**Why it matters:** Real apps need to control object lifecycle (singleton connections vs new objects per request).
- Learn: `@Configuration`, `@Bean`, bean scopes (singleton, prototype), `@Value`, `application.properties`
- **Hands-on:** Externalize a config value (like an API key or threshold) into `application.properties` and inject it with `@Value`. Try switching a bean scope and observe the behavior difference.

### Day 4 — Spring Boot Introduction
**Why it matters:** Spring Boot removes the painful manual configuration of "plain Spring" — auto-configuration, embedded server, starters.
- Learn: `spring-boot-starter-*`, `@SpringBootApplication`, auto-configuration, embedded Tomcat
- **Hands-on:** Create your first Spring Boot app via [start.spring.io](https://start.spring.io). Add a `CommandLineRunner` that prints "Hello Backend" on startup. Understand what happens when you run `mvn spring-boot:run`.

### Day 5 — REST Basics & Your First Controller
**Why it matters:** REST APIs are how frontend, mobile apps, and other services talk to your backend. This is the core skill.
- Learn: `@RestController`, `@GetMapping`, `@PostMapping`, `@RequestBody`, `@PathVariable`, `@RequestParam`
- **Hands-on:** Build a simple in-memory `Student` CRUD API using a `List<Student>` (no DB yet). Test all endpoints in Postman.

---

## Phase 2: Real Persistence Layer (Days 6–10)

### Day 6 — Databases + JDBC Recap
**Why it matters:** Before Spring Data JPA feels magical, you should understand what it's abstracting away.
- Learn/refresh: Basic SQL (SELECT, JOIN, WHERE, INSERT, UPDATE), what JDBC does manually (Connection, Statement, ResultSet)
- **Hands-on:** Install MySQL or PostgreSQL locally (or use Docker). Write and run 10 SQL queries on a sample table manually in a DB client.

### Day 7 — Spring Data JPA & Hibernate
**Why it matters:** This is the #1 most-used backend skill. It maps Java objects to database tables, so you rarely write raw SQL.
- Learn: `@Entity`, `@Id`, `@GeneratedValue`, `JpaRepository`, ORM concept
- **Hands-on:** Convert your `Student` API from Day 5 to persist real data using JPA + MySQL. CRUD operations should now survive app restarts.

### Day 8 — Relationships in JPA
**Why it matters:** Real-world data is relational — Orders belong to Users, Users have Addresses, etc.
- Learn: `@OneToMany`, `@ManyToOne`, `@ManyToMany`, cascading, fetch types (LAZY vs EAGER)
- **Hands-on:** Extend your project: add a `Course` entity, and make `Student` ↔ `Course` a many-to-many relationship. Fetch a student along with their enrolled courses.

### Day 9 — Custom Queries & JPQL
**Why it matters:** Not every query fits the default `findById`/`findAll` methods.
- Learn: Derived query methods (`findByNameAndAge`), `@Query`, JPQL vs native SQL
- **Hands-on:** Write 3 custom queries (e.g., "find students older than X enrolled in a specific course").

### Day 10 — DTOs, Validation & Exception Handling
**Why it matters:** You should never expose your database entities directly to the outside world — this is a real production practice.
- Learn: DTO pattern, `@Valid`, Bean Validation (`@NotNull`, `@Size`, `@Email`), `@ControllerAdvice`, `@ExceptionHandler`
- **Hands-on:** Refactor your API to use Request/Response DTOs. Add validation with meaningful error messages. Add a global exception handler that returns clean JSON errors (not stack traces).

---

## Phase 3: Making It Production-Grade (Days 11–15)

### Day 11 — Layered Architecture (Controller–Service–Repository)
**Why it matters:** This is how every real Spring Boot codebase is structured — separation of concerns.
- Learn: Why logic doesn't belong in controllers, interface-based service design
- **Hands-on:** Refactor your project into clean `controller`, `service`, `repository`, `dto`, `entity`, `exception` packages.

### Day 12 — Spring Security Basics
**Why it matters:** Almost every real API needs authentication. This is non-negotiable for a job-ready backend dev.
- Learn: `SecurityFilterChain`, `UserDetailsService`, password encoding (BCrypt), basic auth
- **Hands-on:** Add username/password login to your project. Protect certain endpoints so only authenticated users can access them.

### Day 13 — JWT Authentication
**Why it matters:** Real APIs (mobile apps, SPAs) use token-based auth, not sessions.
- Learn: JWT structure, stateless auth, filters
- **Hands-on:** Implement JWT login: on successful login, return a token; protect endpoints using a JWT filter that validates the token on each request.

### Day 14 — Testing (JUnit + Mockito)
**Why it matters:** Employers care a LOT about this. Untested code is not "production-ready" code.
- Learn: `@SpringBootTest`, `@WebMvcTest`, `@MockBean`, Mockito basics, writing unit vs integration tests
- **Hands-on:** Write unit tests for your service layer (mock the repository) and one integration test for a controller endpoint.

### Day 15 — Logging, Profiles & Configuration Management
**Why it matters:** Debugging a live server without logs is impossible; different environments (dev/prod) need different configs.
- Learn: SLF4J/Logback, `application-dev.properties` vs `application-prod.properties`, `@Profile`
- **Hands-on:** Add proper logging (info/error/debug) across your service layer. Set up dev and prod profiles with different DB configs.

---

## Phase 4: Capstone Project + Extras (Days 16–20)

### Day 16–18 — Capstone Project (build end-to-end)
Pick ONE realistic project and apply everything above together. Suggestions:
- **Library Management System** (Books, Members, Borrow/Return, overdue rules)
- **E-commerce Mini Backend** (Products, Cart, Orders, Users with JWT auth)
- **Task/Project Tracker** (Users, Projects, Tasks, Roles)

Build with: layered architecture → JPA entities + relationships → validation → global exception handling → JWT security → unit tests → logging.

### Day 19 — API Documentation + Postman Collection
**Why it matters:** Real teams document APIs so frontend devs and other services can consume them without reading your code.
- Learn: Swagger/OpenAPI (`springdoc-openapi`)
- **Hands-on:** Add Swagger UI to your capstone project. Create a Postman collection with all endpoints, sample requests, and example responses.

### Day 20 — Deployment + Wrap-up
**Why it matters:** A backend that only runs on `localhost` isn't a portfolio piece yet.
- Learn: Building a JAR with Maven, Dockerizing a Spring Boot app (basic `Dockerfile`), deploying to a free platform (Render, Railway, or AWS free tier)
- **Hands-on:** Containerize your capstone app with Docker, deploy it, and hit your live API from Postman.
- **Wrap-up:** Push the whole project to GitHub with a proper `README.md` explaining architecture, endpoints, and setup steps.

---

## What Comes After Day 20 (Natural Next Steps)
- **Multithreading & Concurrency** — now revisit this. You'll understand *why* it matters once you've seen requests hit your server (e.g., thread pools in Tomcat, `@Async` in Spring, handling concurrent DB writes).
- **Caching** — Redis + `@Cacheable`
- **Messaging** — Kafka or RabbitMQ for async communication between services
- **Microservices** — Spring Cloud, service discovery, API Gateway
- **CI/CD** — GitHub Actions to auto-build/test/deploy

## Daily Habit Suggestion
Spend roughly: 40% learning concept → 60% typing code yourself (not copy-pasting). At the end of each day, ask yourself: *"Could I explain in an interview why this concept exists and what problem it solves?"* If not, that's the thing to revisit before moving to the next day.