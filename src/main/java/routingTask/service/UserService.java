package routingTask.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import routingTask.dto.UserAddRequestDto;
import routingTask.repository.DataSourceRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSourceRepository dataSourceRepository;
    private final SaveUsersService service;

    public void insertUser(List<UserAddRequestDto> userAddRequestDtos) {
        val countries = userAddRequestDtos.stream()
                .map(UserAddRequestDto::getNationality)
                .distinct()
                .collect(toList());

        val connectionsByCountries = dataSourceRepository.findConnectionsByCountries(countries);
        connectionsByCountries.forEach(c -> service.saveUser(c, userAddRequestDtos));
    }
}
