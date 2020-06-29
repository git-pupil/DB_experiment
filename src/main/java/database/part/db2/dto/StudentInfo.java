package database.part.db2.dto;

import lombok.Data;

@Data
public class StudentInfo {
    private Long id;
    private String name;
    private String sex;
    private String email;
    private String classId;
    private String college;
    private String major;
}
