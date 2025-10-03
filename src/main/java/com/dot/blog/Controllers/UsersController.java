package com.dot.blog.Controllers;

import com.dot.blog.DTO.UsersDto;
import com.dot.blog.Models.Users;
import com.dot.blog.Services.JwtService;
import com.dot.blog.Services.UsersService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Users user){
        return usersService.register(user);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Users user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                user = usersService.findByEmail(user.getEmail());
                String token = jwtService.genrateToken(user.getId(),user.getEmail()); // your token logic
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
         } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody Users user){
        Users requiredUser=usersService.update(user.getId());
        if(requiredUser!=null){
            user.setPassword(requiredUser.getPassword());
            user.setComments(requiredUser.getComments());
            user.setLikes(requiredUser.getLikes());
            user.setUpdatedAt(requiredUser.getUpdatedAt());
            user.setCreatedAt(requiredUser.getCreatedAt());
            user.setShares(requiredUser.getShares());
            user.setPosts(requiredUser.getPosts());
        }
        return usersService.updateUser(user);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return usersService.findById(id);
    }

    @DeleteMapping("deleteAccount/{id}")
    public ResponseEntity<?> delete(int id){
        return usersService.deleteUser(id);
    }


}
