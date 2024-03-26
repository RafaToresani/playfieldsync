package com.playfieldsync.repositories.user;

import com.playfieldsync.entities.user.UserContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactInfoRepository extends JpaRepository<UserContactInfo, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByDni(String dni);
}
