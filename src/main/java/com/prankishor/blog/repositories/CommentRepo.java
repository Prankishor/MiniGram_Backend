package com.prankishor.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prankishor.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
