package com.dot.blog.Repositories.dot;

import com.dot.blog.Models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepo extends JpaRepository<Tags,Long> {
    Tags findByName(String name);
}
