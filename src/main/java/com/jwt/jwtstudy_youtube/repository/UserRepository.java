package com.jwt.jwtstudy_youtube.repository;

import com.jwt.jwtstudy_youtube.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
