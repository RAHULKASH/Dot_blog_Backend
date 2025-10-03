package com.dot.blog.Controllers.dot;

import com.dot.blog.DTO.LikesDto;
import com.dot.blog.DTO.PostsDTO;
import com.dot.blog.Models.Likes;
import com.dot.blog.Models.Posts;
import com.dot.blog.Services.dot.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("api/likes/")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("addLike")
    public ResponseEntity<?> addLike(@RequestBody Likes like){
        return likeService.addLike(like);
    }

    @DeleteMapping("removeLike/{user_id}/{post_id}")
    public ResponseEntity<String> removeLike(@PathVariable long user_id , @PathVariable long post_id){
        String response=likeService.removeLike(user_id,post_id);
        if(response != null){
            return ResponseEntity.ok()
                    .body(response);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @GetMapping("isLiked/{user_id}/{post_id}")
    public ResponseEntity<Boolean> isLiked(@PathVariable long user_id , @PathVariable long post_id){
        return likeService.isLiked(user_id,post_id);
    }

    @GetMapping("userLikes/{user_id}")
    public ResponseEntity<?>findByUserId(@PathVariable long user_id){
        return likeService.findAllByUserId(user_id);
    }

    @GetMapping("postLikes")
    public ResponseEntity<?> findByPostId(@PathVariable long post_id){
        return likeService.findAllByPostId(post_id);
    }


}
