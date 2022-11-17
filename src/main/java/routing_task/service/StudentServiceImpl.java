package routing_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import routing_task.model.dbo.StudentDbo;
import routing_task.model.dto.StudentResponseDto;
import routing_task.repository.StudentRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponseDto> selectAll() {
        return  studentRepository.findAll().stream()
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
