package database.part.db2.mapper;

import database.part.db2.entity.Class;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassMapper {
    public Class findById(Long id);
}
