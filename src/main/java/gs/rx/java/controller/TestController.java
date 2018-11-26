package gs.rx.java.controller;

import gs.rx.java.model.Summary;
import gs.rx.java.service.TestService;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    ExecutorService executor = Executors.newFixedThreadPool(10);

    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(path = "/health")
    public ResponseEntity health() {
        return ResponseEntity.ok("I'm ok...");
    }

    @GetMapping(path = "/greeting")
    public DeferredResult<String> greeting(@RequestParam("name") String name) {
        logger.info("Greeting requested...");

        final DeferredResult<String> result = new DeferredResult<>();
        testService.greeting(name)
                .subscribeOn(Schedulers.from(executor))
                .subscribe(
                        result::setResult,
                        error -> logger.error(error.getMessage())
                );

        logger.info("Greeting sent...");
        return result;
    }

    @GetMapping(path = "/order")
    public DeferredResult<List<String>> findOrders(@RequestParam("customerId") Integer customerId) {
        logger.info("Orders requested...");

        final DeferredResult<List<String>> result = new DeferredResult<>();
        testService.findOrders(customerId)
                .subscribeOn(Schedulers.from(executor))
                .subscribe(
                        result::setResult,
                        error -> logger.error(error.getMessage())
                );

        logger.info("Orders sent...");
        return result;
    }

    @GetMapping(path = "/summary")
    public DeferredResult<Summary> findOrders(@RequestParam("name") String name, @RequestParam("customerId") Integer customerId) {
        logger.info("Summary requested...");

        final DeferredResult<Summary> result = new DeferredResult<>();
        testService.findSummary(name, customerId)
                .subscribeOn(Schedulers.from(executor))
                .subscribe(
                        result::setResult,
                        error -> logger.error(error.getMessage())
                );

        logger.info("Summary sent...");
        return result;
    }

    /**
     * Example using ForkJoinPool
     *
     * @return
     */
    @GetMapping(path = "/customer")
    public DeferredResult<List<Integer>> findCustomers() {
        logger.info("Customers requested...");

        final DeferredResult<List<Integer>> result = new DeferredResult<>();

        ForkJoinPool.commonPool().submit(() -> {
            try {
                Thread.sleep(6000);
                logger.info("Finding customers");
                result.setResult(Arrays.asList(1, 2, 3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        logger.info("Customers sent...");
        return result;
    }
}
