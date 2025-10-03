package com.dot.blog.Repositories;

import com.dot.blog.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {

   public Users findByEmail(String Email);
}
