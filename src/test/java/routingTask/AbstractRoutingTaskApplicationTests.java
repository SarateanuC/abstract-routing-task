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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import routingTask.dto.UserAddRequestDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
//@SqlGroup(
//        @Sql(scripts = {"/sql/20221130_create-database.sql",
//                "/sql/20221130_create-extension.sql",
//                "/sql/20221130_create-table.sql",
//                "/sql/20221130_create-user-table.sql",
//                "/sql/20221130_insert-db.sql"
//        }))
class AbstractRoutingTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @SneakyThrows
    void testSuccessfulTransaction() {
        UserAddRequestDto userAddRequestDto1 = UserAddRequestDto.builder()
                .gender("M")
                .birthdate("date1")
                .nationality("MD")
                .firstName("Smith")
                .lastName("Kevin")
                .password("123")
                .userName("kiwi")
                .build();
        UserAddRequestDto userAddRequestDto2 = UserAddRequestDto.builder()
                .gender("M")
                .birthdate("date2")
                .nationality("UK")
                .firstName("Stone")
                .lastName("Bill")
                .password("12")
                .userName("BILLborded89")
                .build();
        List<UserAddRequestDto> list = List.of(userAddRequestDto1, userAddRequestDto2);
        mockMvc.perform(post("http://localhost:8082/api/user/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @SneakyThrows
    void testTransactionRollback() {
        UserAddRequestDto userAddRequestDto1 = UserAddRequestDto.builder()
                .gender("M")
                .birthdate("date1")
                .nationality("MD")
                .firstName("Smith")
                .lastName("Kevin")
                .password("")
                .userName("kiwi")
                .build();
        UserAddRequestDto userAddRequestDto2 = UserAddRequestDto.builder()
                .gender("M")
                .birthdate("date2")
                .nationality("UK")
                .firstName("Stone")
                .lastName("Bill")
                .password("12")
                .userName("BILLborded89")
                .build();

        List<UserAddRequestDto> list = List.of(userAddRequestDto1, userAddRequestDto2);
        mockMvc.perform(post("http://localhost:8082/api/user/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isInternalServerError());
        //.andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));

    }

}
