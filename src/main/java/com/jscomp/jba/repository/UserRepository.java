package com.jscomp.jba.repository;

import com.jscomp.jba.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}