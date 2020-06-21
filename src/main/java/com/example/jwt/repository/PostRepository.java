package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jwt.entity.Post;
import com.example.jwt.entity.User;

public interface PostRepository  extends JpaRepository<Post, Long>{
}
