package database.part.db2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Course {
    private Long id;
    private String name;

    @JsonIgnore
    private Long teacherId;
}
