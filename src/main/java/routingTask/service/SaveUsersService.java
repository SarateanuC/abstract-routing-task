package routingTask.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
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

//    public void saveUser(DbConnection connection, List<UserAddRequestDto> userAddRequestDtos) {
//        dataSourceRouting.addConnection(connection);
//        val collect = userAddRequestDtos.stream()
//                .filter(u -> u.getNationality().equals(connection.getId()))
//                .map(user -> User.builder()
//                        .firstName(user.getFirstName())
//                        .gender(user.getGender())
//                        .lastName(user.getLastName())
//                        .password(user.getPassword())
//                        .birthdate(user.getBirthdate())
//                        .userName(user.getUserName())
//                        .nationality(user.getNationality())
//                        .build()).collect(Collectors.toList());
//        userRepository.saveAll(collect);
//        dataSourceRouting.closeConnection();
//
//    }



}
