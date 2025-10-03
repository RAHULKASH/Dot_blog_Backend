package com.dot.blog.Repositories.dot;

import com.dot.blog.Models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Long> {

   public List<Comments> findAllByPostId(long postId);

   public List<Comments> findAllByAuthorId(long userId);
}
