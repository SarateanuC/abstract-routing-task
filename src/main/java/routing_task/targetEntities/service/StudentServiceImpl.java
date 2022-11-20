package routing_task.targetEntities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import routing_task.targetEntities.model.StudentDbo;
import routing_task.targetEntities.repository.StudentRepository;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;


    @Override
    public void save() {
        studentRepository.save(StudentDbo.builder().name("Test").age(16).build());
    }
}
