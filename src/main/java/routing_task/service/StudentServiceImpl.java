package routing_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import routing_task.dto.StudentResponseDto;
import routing_task.mapper.StudentMapper;
import routing_task.model.StudentDbo;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponseDto> selectAll() {
        return studentMapper.selectALL().stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    private StudentResponseDto convertToDto(StudentDbo studentDbo) {
        return StudentResponseDto.builder()
                .age(studentDbo.getAge())
                .id(studentDbo.getId())
                .name(studentDbo.getName())
                .build();
    }
}
