package com.playfieldsync.repositories.complex;

import com.playfieldsync.entities.complex.ComplexContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexContactInfoRepository extends JpaRepository<ComplexContactInfo, Long> {

    Boolean existsByEmail(String username);
}
