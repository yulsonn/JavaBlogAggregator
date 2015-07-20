package com.jscomp.jba.repository;

import com.jscomp.jba.entity.Blog;
import com.jscomp.jba.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findByUser(User user);
}