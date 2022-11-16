package routing_task.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    private Integer id;
    private String name;
    private Integer age;
}
