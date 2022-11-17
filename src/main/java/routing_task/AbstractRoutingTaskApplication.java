package routing_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AbstractRoutingTaskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AbstractRoutingTaskApplication.class, args);
    }
}

