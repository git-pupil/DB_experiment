package database.part.db2.dto;

import lombok.Data;

@Data
public class CourseInfo {
    private Long id;
    private String name;
    private Long teacherId;
    private String college;
}
