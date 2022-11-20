package routing_task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import routing_task.config.DataSourceContextHolder;
import routing_task.config.enums.DataSourceEnum;
import routing_task.targetEntities.service.StudentService;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final DataSourceContextHolder dataSourceContextHolder;

    @GetMapping(value = "/test")
    public String getDb(@RequestParam("dataSourceType") DataSourceEnum dataSourceType) {
        dataSourceContextHolder.setBranchContext(dataSourceType);
        studentService.save();
        dataSourceContextHolder.clearBranchContext();
        return "Applied db " + dataSourceType;
    }
}
