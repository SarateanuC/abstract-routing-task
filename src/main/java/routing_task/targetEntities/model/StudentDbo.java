package routing_task.targetEntities.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDbo {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer age;
}
