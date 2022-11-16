package routing_task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import routing_task.contextHolder.DataSourceContextHolder;
import routing_task.dto.StudentResponseDto;
import routing_task.service.StudentService;

import java.util.List;

import static routing_task.context.DataSourceEnum.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final DataSourceContextHolder dataSourceContextHolder;

    @GetMapping(value = "/all/{dataSourceType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentResponseDto> getAllEmployeeDetails(@PathVariable("dataSourceType") String dataSourceType) {
        if (DATASOURCE_ONE.toString().equals(dataSourceType)) {
            dataSourceContextHolder.setBranchContext(DATASOURCE_ONE);
        }
        if (DATASOURCE_TWO.toString().equals(dataSourceType)) {
            dataSourceContextHolder.setBranchContext(DATASOURCE_TWO);
        }
        if (DATASOURCE_THREE.toString().equals(dataSourceType)) {
            dataSourceContextHolder.setBranchContext(DATASOURCE_THREE);
        }
        dataSourceContextHolder.setBranchContext(PRIMARY_DATASOURCE);
        return studentService.selectAll();
    }
}
