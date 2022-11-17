package routing_task.model.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    private UUID id;
    private String name;
    private Integer age;
}
