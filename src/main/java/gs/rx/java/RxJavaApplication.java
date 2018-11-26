package gs.rx.java;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@SpringBootApplication
public class RxJavaApplication {

    private static Logger logger = LoggerFactory.getLogger(RxJavaApplication.class);

    public static void main(String... args) {
        logger.info("RxJavaApplication is starting...");
        SpringApplication.run(RxJavaApplication.class, args);
        logger.info("RxJavaApplication has finished starting...");
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
