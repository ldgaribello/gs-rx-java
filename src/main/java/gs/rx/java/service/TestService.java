package gs.rx.java.service;

import gs.rx.java.model.Summary;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    public Observable<List<String>> findOrders(Integer customerId) {
        return Observable.fromCallable(() -> {
            logger.info("Finding orders for customer with id {}", customerId);
            Thread.sleep(8000);
            logger.info("Orders for customer with id {} founded", customerId);

            return Arrays.asList("Order 1", "Order 2", "Order 3");
        });
    }

    public Single<Summary> findSummary(String name, Integer customerId) {
        return Single.zip(greeting(name), Single.fromObservable(findOrders(customerId)),
                (greeting, orders) -> {
                    logger.info("Creating summary for {}", name);

                    Summary summary = new Summary();
                    summary.setGreeting(greeting);
                    summary.setOrders(orders);
                    return summary;
                });
    }
}
