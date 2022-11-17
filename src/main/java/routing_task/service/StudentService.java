package routing_task.service;

import routing_task.model.dto.StudentResponseDto;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> selectAll();
}
