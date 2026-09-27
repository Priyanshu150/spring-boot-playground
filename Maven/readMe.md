Why do we need Maven at all?

Imagine you're writing a plain Java project without any build tool. The moment your project needs an external library — say, for JSON parsing or HTTP calls — you'd have to:

Manually go download the .jar file for that library from somewhere
Add it to your classpath by hand
If that library itself depends on other libraries (which almost all do), go find and download those too
Repeat this every time you add a new dependency, and repeat it again for every teammate's machine
Manually remember the exact sequence of commands to compile, test, and package your code into a runnable artifact

This works for a 3-class toy project. It falls apart completely on a real project with 50+ dependencies and multiple developers. Maven exists to automate this whole mess — dependency fetching, project structure, build steps — so you declare what you want in a config file, and Maven figures out how to get it done.

Where it fits in the picture

Think of it as sitting between "you writing code" and "a working, deployable application":

Your source code  →  Maven (build tool)  →  Compiled classes / JAR / WAR
                         ↑
              Fetches dependencies automatically
              from repositories

It's not part of your application logic at all — it's infrastructure that manages how your project is built, what libraries it uses, and how it's packaged. This is why it's the very first thing to learn before Spring: Spring itself is just a bunch of JAR files, and Maven is what pulls them in.

Now let's connect your specific points

JAR files — A JAR is just a zipped bundle of compiled .class files (plus metadata). It's the "packaged product" of a Java project. The problem Maven solves isn't the JAR format itself, but managing JARs — hundreds of them, with version conflicts, at scale.

Maven Fundamentals — Beyond dependencies, Maven also enforces a standard project structure (src/main/java, src/test/java, src/main/resources, etc.). This matters more than it sounds: because every Maven project looks the same, any Java developer can open any Maven project and immediately know where things live — no onboarding needed to understand "where's the code."

pom.xml — This is the heart of it. Think of pom.xml as your project's contract: "here's my project's identity (groupId/artifactId/version), here's what libraries I need, here's how I want to be built." You declare the desired state; Maven executes it.

Dependency Management — This is Maven's killer feature. You write one line like:

xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

and Maven doesn't just fetch that one JAR — it resolves the entire dependency tree that library needs (this is "transitive dependencies"). Spring Boot Starter Web alone pulls in Tomcat, Jackson, Spring MVC, and more — you never touch any of that manually.

Repositories — Two layers, like a cache hierarchy:

Local repo (~/.m2) — your machine's personal cache. Once a library is downloaded once, it's reused for every project on your machine.
Central repo — the public internet source Maven pulls from if it's not already in your local cache.

This two-tier system is why your second Maven project builds noticeably faster than your first — most dependencies are already cached locally.

Maven Lifecycle — This is the automation of "the steps you'd otherwise do by hand":

Clean — wipes previous build output
Default — the real build pipeline: compile → test → package → install → deploy
Site — generates project documentation (rarely used day-to-day)

When you run mvn clean package, Maven runs through validate → compile → test → package in order — you don't manually invoke javac, then run your test runner, then zip it into a JAR. One command, all steps, always in the correct order.

Archetypes — These are just project templates (e.g., "give me a standard Maven web app skeleton"). Less commonly used once you start using Spring Initializr for Spring Boot projects specifically, but useful to know they exist as a general Maven concept.

The mental model to hold onto

Maven answers three questions for any Java project:

What does my project need? (dependencies, declared in pom.xml)
Where does it come from? (repositories — local cache first, then central)
How do I turn source code into a runnable thing? (lifecycle — compile, test, package)