package com.dot.blog.Services.dot;

import com.dot.blog.DTO.CommentRequest;
import com.dot.blog.DTO.CommentsDto;
import com.dot.blog.DTO.UsersDto;
import com.dot.blog.Models.Comments;
import com.dot.blog.Models.Posts;
import com.dot.blog.Models.Users;
import com.dot.blog.Repositories.UsersRepo;
import com.dot.blog.Repositories.dot.CommentRepo;
import com.dot.blog.Repositories.dot.PostRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PostRepo postRepo;

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

    public ResponseEntity<?> postComment(CommentRequest comment) {
        try {
            Users user = usersRepo.findById(comment.getUser_id()).orElse(null);
            Posts post = postRepo.findById(comment.getPost_id()).orElse(null);

            if (user == null || post == null) {
                ResponseEntity.badRequest()
                        .body("Comments are not allowed");
            }

            Comments newComment = new Comments(comment.getContent(), user, post);
            Comments data = commentRepo.save(newComment);
            CommentsDto dto= new CommentsDto(
                    data.getId(),
                    data.getContent(),
                    getUser(data.getAuthor()),
                    data.getPost().getId(),
                    data.getCreatedAt(),
                    data.getUpdatedAt()
            );
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public String removeComment(Comments comment) {
        try {
            commentRepo.delete(comment);
            return "Comment Removed";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public ResponseEntity<?> findAllByAuthorId(long author_id){
        try{
            List<Comments> comments = commentRepo.findAllByAuthorId(author_id);
            List<CommentsDto> commentsDtos=new ArrayList<>();
            for(Comments item:comments){
                commentsDtos.add(new CommentsDto(item.getId(),item.getContent(),getUser(item.getAuthor()),item.getPost().getId(),item.getCreatedAt(),item.getUpdatedAt()));
            }
            return ResponseEntity.ok(commentsDtos);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findAllByPostId(long postId) {
        try {
            List<Comments> comments = commentRepo.findAllByPostId(postId);
            if (comments.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            List<CommentsDto>commentList=comments.stream()
                    .map(item -> new CommentsDto(
                            item.getId(),
                            item.getContent(),
                            getUser(item.getAuthor()),
                            item.getPost().getId(),
                            item.getCreatedAt(),
                            item.getUpdatedAt()
                    ))
                    .toList();

            return ResponseEntity.ok(commentList);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
