package database.part.db2.dto;

import database.part.db2.entity.Teacher;
import lombok.Data;

//学生需要
@Data
public class GradeWithCourse {
    private Long id;
    private String name;
    private String teacherName;
    private int point;
}
