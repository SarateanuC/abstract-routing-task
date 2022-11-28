package routingTask.requestDto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DboConnectionAddRequest {
    private String id;
    private String url;
    private String username;
    private String password;
}
