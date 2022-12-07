package routingTask.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "dbconnections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbConnection {
    @Id
    @NotBlank(message = "Id must not be blank")
    private String id;
    @NotBlank(message = "URL must not be blank")
    private String url;
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
