package com.dot.blog.Repositories.dot;

import com.dot.blog.Models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepo extends JpaRepository<Categories,Long> {
   public Categories findByName(String name);
}
