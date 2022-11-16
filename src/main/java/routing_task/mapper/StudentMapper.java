package routing_task.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import routing_task.model.StudentDbo;

import java.util.List;

@Mapper
public interface StudentMapper {
    String SELECT_FROM_STUDENT = "SELECT * FROM Student";

    @Select(SELECT_FROM_STUDENT)
    List<StudentDbo> selectALL();
}
