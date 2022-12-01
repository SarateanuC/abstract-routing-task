package routingTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import routingTask.dto.UserAddRequestDto;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AbstractRoutingTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Container
    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("database_connector");


    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES_SQL_CONTAINER::getDriverClassName);

    }

    @Test
    @SneakyThrows
    void testSuccessfulTransaction() {
        UserAddRequestDto userAddRequestDto1 = UserAddRequestDto.builder()
                .gender("M")
                .birthdate("date1")
                .nationality("MD")
                .firstName("Smith")
                .lastName("Kevi")
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
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));

    }

}
