package com.playfieldsync.repositories.complex;

import com.playfieldsync.entities.complex.ComplexPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexPhoneNumberRepository extends JpaRepository<ComplexPhoneNumber, Long> {
}
