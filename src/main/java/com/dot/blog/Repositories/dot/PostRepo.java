package com.dot.blog.Repositories.dot;

import com.dot.blog.Models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Posts,Long> {
    List<Posts> findAllByAuthorId(long autherId);

    List<Posts> findByAuthorId(long autherId);

    List<Posts> findByTitleContainingIgnoreCase(String keyword);

    void deleteAllByAuthorId(long authorId);

    @Query("SELECT p FROM Posts p ORDER BY p.viewCount DESC LIMIT 15")
    List<Posts> findPopular();

    public List<Posts> findByCategoryId(long id);

    boolean existsBySlug(String slug);
}
