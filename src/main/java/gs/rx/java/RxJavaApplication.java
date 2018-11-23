package gs.rx.java;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RxJavaApplication {

    private static Logger logger = LoggerFactory.getLogger(RxJavaApplication.class);

    public static void main(String... args) {
        logger.info("RxJavaApplication is starting...");
        SpringApplication.run(RxJavaApplication.class, args);
        logger.info("RxJavaApplication has finished starting...");
    }
}
