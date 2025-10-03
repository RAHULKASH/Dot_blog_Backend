package com.dot.blog.Repositories.dot;

import com.dot.blog.Models.Shares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepo extends JpaRepository<Shares,Long> {

    List<Shares> findByPostId(long postId);

}
