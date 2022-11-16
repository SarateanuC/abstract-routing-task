package routing_task.service;

import routing_task.dto.StudentResponseDto;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> selectAll();
}
