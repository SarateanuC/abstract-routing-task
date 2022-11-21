package routing_task.main.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NextDBConnection {
    @Id
    private String dataSourceEnum;
    private String url;
    private String username;
    private String password;
}
