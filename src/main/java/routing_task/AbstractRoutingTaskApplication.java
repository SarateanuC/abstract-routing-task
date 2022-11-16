package routing_task;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import routing_task.model.StudentDbo;
import routing_task.routing.DataSourceRouting;
@MappedTypes(StudentDbo.class)
@MapperScan("routing_task.mapper.StudentMapper")
@SpringBootApplication
public class AbstractRoutingTaskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AbstractRoutingTaskApplication.class, args);
    }

}
