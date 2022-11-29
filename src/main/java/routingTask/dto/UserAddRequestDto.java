package routingTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRequestDto {
    @NotBlank(message = "Firstname must not be blank")
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank(message = "Lastname must not be blank")
    private String last_name;
    private String sex;
    private String date_of_birth;
    @NotBlank(message = "Nationality req must not be blank")
    private String nationality;
    @NotBlank(message = "Username must not be blank")
    private String user_name;
    @NotBlank(message = "Password req must not be blank")
    private String password;
}
