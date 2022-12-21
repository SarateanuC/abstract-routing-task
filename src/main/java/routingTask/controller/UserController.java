package routingTask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import routingTask.dto.UserAddRequestDto;
import routingTask.service.UserService;

import javax.transaction.SystemException;
import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public void addStudents(@Valid @RequestBody List<UserAddRequestDto> userAddRequestDtos) throws SystemException {
        userService.insertUser(userAddRequestDtos);
    }
}
