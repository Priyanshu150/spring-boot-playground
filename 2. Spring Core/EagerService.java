/*
 * Initialization Ways in Spring
    1. Eager Initialization (Default)
        - Beans are created at the time of container startup.
    2. Lazy Initialization
        - Beans are created when they are requested for the first time.
    
    there is no @Eager annotation in Spring. Eager initialization is the default behavior for singleton beans. 
    You only opt out of it using @Lazy. (You can write @Lazy(false) to state eager explicitly, but it’s rarely needed.)
 */

// Eager Initialization (Default)

@Component
public class EagerService {
    public EagerService() {
        System.out.println("EagerService created");
    }
}

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting context...");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Context ready.");
    }
}

/* Output:
    Starting context...
    EagerService created
    Context ready.

The bean was created before you ever asked for it.

Why eager is the default: problems surface immediately. A missing dependency, bad config,
or a DB connection failure crashes the app at startup,
not at 2 AM when some user first hits that code path. Fail fast is a feature. */

@Component
@Lazy
public class LazyService {
    public LazyService() {
        System.out.println("LazyService created");
    }

    public void doWork() {
        System.out.println("Working...");
    }
}

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting context...");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Context ready.");

        System.out.println("Now requesting LazyService...");
        LazyService service = context.getBean(LazyService.class);
        service.doWork();
    }
}

/*  Output:

Starting context...
Context ready.
Now requesting LazyService...
LazyService created
Working...

“LazyService created” appears only after the context was ready, at the moment getBean was called.

    @Lazy on an injection point

    If an eager bean depends on a lazy bean, the lazy bean gets created anyway 
    (since the eager bean needs it at construction). To keep it truly lazy, put @Lazy on the injection point too:
*/

@Component
public class OrderService {
    private final LazyService lazyService;

    public OrderService(@Lazy LazyService lazyService) {
        this.lazyService = lazyService; // this is a proxy; real bean created on first method call
    }
}

