package gs.rx.java.controller;

import gs.rx.java.service.TestService;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

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
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        greeting -> result.setResult(greeting),
                        error -> logger.error(error.getMessage())
                );

        logger.info("Greeting sent...");
        return result;
    }
}
