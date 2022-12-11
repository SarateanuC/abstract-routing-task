package routingTask.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import routingTask.dto.UserAddRequestDto;
import routingTask.exception.NoSuchCountryException;
import routingTask.repository.DataSourceRepository;
import routingTask.routing.DataSourceRouting;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSourceRepository dataSourceRepository;
    private final SaveUsersService saveUsersService;
    private final DataSourceRouting dataSourceRouting;

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        val countries = userAddRequestDtos.stream()
                .map(UserAddRequestDto::getNationality)
                .distinct()
                .collect(toList());
        val connectionsByCountries = dataSourceRepository.findConnectionsByCountries(countries);
        if (connectionsByCountries.isEmpty()) {
            throw new NoSuchCountryException();
        }
        connectionsByCountries.forEach(c -> saveUsersService.saveUser(c, userAddRequestDtos));
        dataSourceRouting.closeConnection();
    }

//    @Transactional
//    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
//        val countries = userAddRequestDtos.stream()
//                .map(UserAddRequestDto::getNationality)
//                .distinct()
//                .collect(toList());
//        val connectionsByCountries = dataSourceRepository.findConnectionsByCountries(countries);
//        if (connectionsByCountries.isEmpty()) {
//            throw new NoSuchCountryException();
//        }
//        connectionsByCountries.forEach(c -> saveUsersService.saveUser(c, userAddRequestDtos));
//    }
}
