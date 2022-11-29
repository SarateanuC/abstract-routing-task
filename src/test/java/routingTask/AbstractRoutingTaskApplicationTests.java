package routingTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import routingTask.dto.UserAddRequestDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AbstractRoutingTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void testSuccessfulTransaction() {
        UserAddRequestDto userAddRequestDto1 = UserAddRequestDto.builder()
                .sex("M")
                .date_of_birth("date1")
                .nationality("MD")
                .first_name("Smith")
                .last_name("Kevin")
                .password("123")
                .user_name("kiwi")
                .build();
        UserAddRequestDto userAddRequestDto2 = UserAddRequestDto.builder()
                .sex("M")
                .date_of_birth("date2")
                .nationality("UK")
                .first_name("Stone")
                .last_name("Bill")
                .password("12")
                .user_name("BILLborded89")
                .build();
        List<UserAddRequestDto> list = List.of(userAddRequestDto1, userAddRequestDto2);
        mockMvc.perform(post("http://localhost:8082/api/connections/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testTransactionRollback() {
        UserAddRequestDto userAddRequestDto1 = UserAddRequestDto.builder()
                .sex("M")
                .date_of_birth("date1")
                .nationality("MD")
                .first_name("Smith")
                .last_name("Kevin")
                .password("123")
                .user_name("kiwi")
                .build();
        UserAddRequestDto userAddRequestDto2 = UserAddRequestDto.builder()
                .sex("M")
                .date_of_birth("date2")
                .nationality("UK")
                .first_name("Stone")
                .last_name("Bill")
                .password("  ")
                .user_name("BILLborded89")
                .build();
        List<UserAddRequestDto> list = List.of(userAddRequestDto1, userAddRequestDto2);
        mockMvc.perform(post("http://localhost:8082/api/connections/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NestedServletException));
    }

}
