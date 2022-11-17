package routing_task.config;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSource {
    @Id
    @GeneratedValue
    private UUID id;
    private String url;
    private String password;
    private String username;
}
