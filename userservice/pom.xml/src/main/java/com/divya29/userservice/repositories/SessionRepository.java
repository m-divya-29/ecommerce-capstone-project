package com.divya29.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.userservice.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

	Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}
