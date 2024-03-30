package com.playfieldsync.repositories.field;

import com.playfieldsync.entities.field.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findAllByComplexId(Long complexId);
    List<Field> findAllByIsActive(Boolean isActive);
}
