package gs.rx.java.service;

import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private static Logger logger = LoggerFactory.getLogger(TestService.class);

    public Single<String> greeting(String name) {
        return Single.fromCallable(() -> {
            Thread.sleep(10000);
            logger.info("Creating greeting for {}", name);
            return String.format("Hello %s!!!", name);
        });
    }
}
