package database.part.db2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Student {
    private Long id;
    private String name;
    private String sex;
    private String email;

    @JsonIgnore
    private Long classId;
}
