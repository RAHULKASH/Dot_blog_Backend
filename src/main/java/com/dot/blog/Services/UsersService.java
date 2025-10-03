package com.dot.blog.Services;

import com.dot.blog.DTO.UsersDto;
import com.dot.blog.Models.Users;
import com.dot.blog.Repositories.UsersRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    private UsersDto getUser(Users user){
        return new UsersDto(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getProfileImageUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getPosts().size(),
                user.getComments().size(),
                user.getLikes().size(),
                user.getShares().size()
        );
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);



    public ResponseEntity<?> register(Users user){
        try {
            if (user.getPassword().length() < 8) {
                return ResponseEntity
                        .badRequest()
                        .body("Password must be at least 8 characters long");
            }

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Users savedUser = usersRepo.save(user);
            UsersDto dto = getUser(savedUser);

            return ResponseEntity.ok()
                    .body(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> updateUser(Users user){
        try {
            Users savedUser = usersRepo.save(user);
            UsersDto dto = getUser(savedUser);

            return ResponseEntity.ok()
                    .body(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findById(long id){
        try {
            Users user = usersRepo.findById(id).orElse(null);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }

            UsersDto userDto = getUser(user);
            return ResponseEntity.ok(userDto);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteUser(long id) {
        try {
            if (!usersRepo.existsById(id)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }

            usersRepo.deleteById(id);

            return ResponseEntity.ok()
                    .body("You Account has been deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public Users findByEmail(String email) {
        try {
            Users user=usersRepo.findByEmail(email);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public Users update(Long id) {
        return usersRepo.findById(id).orElse(null);
    }
}
