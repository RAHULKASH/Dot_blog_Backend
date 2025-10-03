package com.dot.blog.Controllers.dot;

import com.dot.blog.DTO.CommentRequest;
import com.dot.blog.DTO.CommentsDto;
import com.dot.blog.Models.Comments;
import com.dot.blog.Services.dot.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/comments/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest comment){
        return commentService.postComment(comment);
    }

    @DeleteMapping("removeComment")
    public ResponseEntity<String> removeComment(@RequestBody Comments comment) {
        String response = commentService.removeComment(comment);
        if (response != null) {
            return ResponseEntity.ok()
                    .body("Comment Removed");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("userComments/{user_id}")
    public ResponseEntity<?> findByUserId(@PathVariable long user_id){
        return commentService.findAllByAuthorId(user_id);

    }

    @GetMapping("postsComments/{post_id}")
    public ResponseEntity<?> findByPostId(@PathVariable long post_id){
        return commentService.findAllByPostId(post_id);
    }

}
