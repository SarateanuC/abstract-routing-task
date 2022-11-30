package routingTask.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import routingTask.dto.UserAddRequestDto;
import routingTask.entity.DbConnection;
import routingTask.entity.User;
import routingTask.repository.UserRepository;
import routingTask.routing.DataSourceRouting;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaveUsersService {
    private final DataSourceRouting dataSourceRouting;
    private final UserRepository userRepository;

    @Transactional
    protected void saveUser(DbConnection connection, List<UserAddRequestDto> userAddRequestDtos) {
        val collect = userAddRequestDtos.stream()
                .map(user -> User.builder()
                        .firstName(user.getFirstName())
                        .gender(user.getGender())
                        .last_name(user.getLastName())
                        .password(user.getPassword())
                        .birthdate(user.getBirthdate())
                        .userName(user.getUserName())
                        .nationality(user.getNationality())
                        .build()).collect(Collectors.groupingBy(User::getNationality));
        collect.forEach((key, value) -> {
            if (key.matches(connection.getId())) {
                dataSourceRouting.addConnection(connection);
                userRepository.saveAll(value);
                dataSourceRouting.closeConnection();
            }
        });
    }
}
