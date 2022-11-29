package routingTask.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRequestDto {
    @NotBlank(message = "Firstname must not be blank")
    private String first_name;
    @NotBlank(message = "Lastname must not be blank")
    private String last_name;
    private String sex;
    private String date_of_birth;
    @NotBlank(message = "Nationalityreq must not be blank")
    private String nationality;
    @NotBlank(message = "Username must not be blank")
    private String user_name;
    @NotBlank(message = "Passwordreq must not be blank")
    private String password;
}
