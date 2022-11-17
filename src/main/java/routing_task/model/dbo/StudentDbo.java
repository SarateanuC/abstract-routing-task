package routing_task.model.dbo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDbo {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Integer age;
}
