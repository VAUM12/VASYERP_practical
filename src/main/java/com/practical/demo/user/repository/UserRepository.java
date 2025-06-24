package com.practical.demo.user.repository;

import com.practical.demo.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByEmail(String email);

    boolean existsByEmail(String mail);
}
