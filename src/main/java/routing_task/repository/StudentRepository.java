package routing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import routing_task.model.dbo.StudentDbo;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentDbo, UUID> {
}
