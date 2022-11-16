package routing_task.model;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDbo {
    private Integer id;
    private String name;
    private Integer age;
}
