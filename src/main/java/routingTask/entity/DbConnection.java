package routingTask.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;


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
