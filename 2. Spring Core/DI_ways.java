

public interface PaymentService {
    void pay(double amount);
}

@Component
public class CreditCardPayment implements PaymentService {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via Credit Card");
    }
}

@Component
public class UpiPayment implements PaymentService {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via UPI");
    }
}


// class asks for a PaymentService

@Component
public class OrderService {
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

/* Spring looks for a bean of type PaymentService, finds two, and doesn’t know which to pick. 
It fails at startup with NoUniqueBeanDefinitionException. This is very common in real projects 
(e.g., multiple payment gateways, multiple notification channels like Email/SMS). */

// Solution A: @Primary (“this is the default”)

@Component
@Primary
public class UpiPayment implements PaymentService {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via UPI");
    }
}

// Now whenever someone asks for a PaymentService without being specific, Spring injects UpiPayment

// Solution B: @Qualifier (“I want this specific one”)

@Component
public class OrderService {
    private final PaymentService paymentService;

    public OrderService(@Qualifier("card") PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

/* What if both are used together?

    @Qualifier wins over @Primary. So the common pattern is:

    @Primary on the implementation used 90% of the time (sensible default)
    @Qualifier on the few places that need something different */


@Component
public class RefundService {
    private final PaymentService paymentService;

    // Gets CreditCardPayment even though UpiPayment is @Primary
    public RefundService(@Qualifier("card") PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}