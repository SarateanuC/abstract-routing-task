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
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("sex")
    private String gender;
    @JsonProperty("date_of_birth")
    private String birthdate;
    @NotBlank(message = "Nationality must not be blank")
    private String nationality;
    @NotBlank(message = "Username must not be blank")
    @JsonProperty("user_name")
    private String userName;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
