package routingTask.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    @NotBlank(message = "Firstname must not be blank")
    @Column(value = "first_name")
    private String firstName;
    @NotBlank(message = "Lastname must not be blank")
    @Column(value = "last_name")
    private String lastName;
    private String gender;
    @Column(value = "birth_date")
    private String birthdate;
    @NotBlank(message = "Nationality must not be blank")
    private String nationality;
    @NotBlank(message = "Username must not be blank")
    @Column(value = "user_name")
    private String userName;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
