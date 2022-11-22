package routing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routing_task.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
