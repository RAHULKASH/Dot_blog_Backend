package com.dot.blog.Repositories.dot;

import com.dot.blog.Models.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Likes,Long> {

  public  List<Likes> findAllByUserId(Long userId);

   public List<Likes> findAllByPostId(Long postId);

   public void deleteByUserId(Long id);

    Optional<Likes> findByPostIdAndUserId(long postId, long userId);
}
