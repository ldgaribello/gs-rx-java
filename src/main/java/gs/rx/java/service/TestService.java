package gs.rx.java.service;

import gs.rx.java.model.Summary;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private static Logger logger = LoggerFactory.getLogger(TestService.class);

    public Single<String> greeting(String name) {
        return Single.fromCallable(() -> {
            logger.info("Creating greeting for {}", name);
            Thread.sleep(6000);
            logger.info("Greetings for {} created", name);

            return String.format("Hello %s!!!", name);
        });
    }

    public Observable<String> findOrders(Integer customerId) {
        logger.info("Finding orders for customer with id {}", customerId);

        return Observable
                .fromArray("Order 1", "Order 2", "Order 3")
                .map(order -> {
                    Thread.sleep(2000);
                    logger.info("Order: {} found", order);
                    return order;
                });
    }

    public Single<Summary> findSummary(String name, Integer customerId) {
        return Single.zip(greeting(name), findOrders(customerId).toList(),
                (greeting, orders) -> {
                    logger.info("Creating summary for {}", name);

                    Summary summary = new Summary();
                    summary.setGreeting(greeting);
                    summary.setOrders(orders);
                    return summary;
                });
    }
}
