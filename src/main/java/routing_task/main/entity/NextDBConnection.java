package routing_task.main.entity;

import lombok.*;
import routing_task.config.enums.DataSourceEnum;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NextDBConnection {
    @Id
    @Enumerated(EnumType.STRING)
    private DataSourceEnum dataSourceEnum;
    private String url;
    private String username;
    private String password;
}
