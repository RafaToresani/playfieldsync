package com.playfieldsync.repositories.complex;

import com.playfieldsync.entities.complex.Complex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexRepository extends JpaRepository<Complex, Long> {

    Boolean existsByName(String name);
}
