package routingTask.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "Firstname must not be blank")
    @Column(name = "first_name")
    private String firstName;
    @NotBlank(message = "Lastname must not be blank")
    private String last_name;
    private String gender;
    private String birth_Date;
    @NotBlank(message = "Nationality must not be blank")
    private String nationality;
    @NotBlank(message = "Username must not be blank")
    private String user_name;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
