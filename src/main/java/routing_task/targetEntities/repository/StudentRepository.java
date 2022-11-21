package routing_task.targetEntities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routing_task.targetEntities.model.StudentDbo;

@Repository
public interface StudentRepository extends JpaRepository<StudentDbo, Integer> {
}
