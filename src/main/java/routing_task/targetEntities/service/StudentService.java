package routing_task.targetEntities.service;

import routing_task.targetEntities.model.StudentDbo;

import java.util.List;

public interface StudentService {
    void save();
    List<StudentDbo> getAll();
}
