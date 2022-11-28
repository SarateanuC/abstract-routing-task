package routingTask.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dbconnections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbConnection {
    @Id
    private String id;
    private String url;
    private String username;
    private String password;
}
