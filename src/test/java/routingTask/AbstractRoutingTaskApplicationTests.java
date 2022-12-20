package routingTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import routingTask.dto.UserAddRequestDto;
import routingTask.repository.DataSourceRepository;
import routingTask.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"/sql/init-primary-db.sql"})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
class AbstractRoutingTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DataSourceRepository dataSourceRepository;


    @Container
    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13-alpine"))
                    .withDatabaseName("database_connector_test")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withInitScript("sql/createdb.sql")
                    .withReuse(true);


    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES_SQL_CONTAINER::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @BeforeAll
    public static void init() {
        POSTGRES_SQL_CONTAINER.setPortBindings(Collections.singletonList("60250:5432"));
        POSTGRES_SQL_CONTAINER.start();
    }

    @Test
    void numberOfConnectionsTest() {
        assertThat(dataSourceRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    void testSuccessfulTransaction() {
        System.out.println("hello");
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
        System.out.println(POSTGRES_SQL_CONTAINER.getDatabaseName());
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
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void wrongDbTest() {
        UserAddRequestDto userAddRequestDto = UserAddRequestDto.builder()
                .gender("M")
                .birthdate("date2")
                .nationality("BG")
                .firstName("Stone")
                .lastName("Bill")
                .password("12")
                .userName("BILLborded89")
                .build();
        mockMvc.perform(post("http://localhost:8082/api/user/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(List.of(userAddRequestDto))))
                .andExpect(status().isBadRequest());
    }
}
