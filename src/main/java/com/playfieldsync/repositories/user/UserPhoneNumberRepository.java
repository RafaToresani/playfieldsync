package com.playfieldsync.repositories.user;

import com.playfieldsync.entities.user.UserPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, Long> {
}
