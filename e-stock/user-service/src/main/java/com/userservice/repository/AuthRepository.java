package com.userservice.repository;

import java.util.Optional;

import com.userservice.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * perform user related operations
 *
 */
@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Integer> {
	Optional<AuthEntity> findByUserName(String userName);
}