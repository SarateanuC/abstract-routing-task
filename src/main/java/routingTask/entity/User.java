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
    @Column(name = "last_name")
    private String lastName;
    private String gender;
    @Column(name = "birth_date")
    private String birthdate;
    @NotBlank(message = "Nationality must not be blank")
    private String nationality;
    @NotBlank(message = "Username must not be blank")
    @Column(name = "user_name")
    private String userName;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
