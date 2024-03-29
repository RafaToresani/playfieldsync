package com.playfieldsync.repositories.complex;

import com.playfieldsync.entities.complex.ComplexAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexAddressRepository extends JpaRepository<ComplexAddress, Long> {
}
