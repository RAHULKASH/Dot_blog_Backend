package com.dot.blog.Controllers.dot;

import com.dot.blog.DTO.PostsDTO;
import com.dot.blog.Models.Categories;
import com.dot.blog.Models.Posts;
import com.dot.blog.Models.Tags;
import com.dot.blog.Models.Users;
import com.dot.blog.Repositories.UsersRepo;
import com.dot.blog.Repositories.dot.CategoriesRepo;
import com.dot.blog.Repositories.dot.TagsRepo;
import com.dot.blog.Services.dot.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoriesRepo categoriesRepo;

    @Autowired
    private TagsRepo tagsRepo;

    @Autowired
    private UsersRepo usersRepo;

    private Set<Tags> getTags(Set<String> tagNames){
        Set<Tags> tagSet = new HashSet<>();
        for (String name : tagNames) {
            Tags tag=tagsRepo.findByName(name);
            if(tag!=null) tagSet.add(tag);
        }
        return tagSet;
    }

    private Categories getCategory(String name){
        return categoriesRepo.findByName(name);
    }

    private Users getUser(long id){
        return usersRepo.findById(id).orElse(null);
    }


    private Posts setPost(PostsDTO post){

       return new Posts(
               post.getTitle(),
               post.getContent(),
               post.getSlug(),
               getUser(post.getAuthor().getId()),
               getTags(post.getTagNames()),
               getCategory(post.getCategoryName())
        );

    }


    @PostMapping("addPost")
    public ResponseEntity<?> addPost(@RequestBody PostsDTO post){
        Posts newPost=setPost(post);
        return postService.addPost(newPost);
    }

    @GetMapping("popular")
    public ResponseEntity<?> popular(){
        return postService.findPopular();
    }

    @DeleteMapping("removePost")
    public ResponseEntity<String> removePost(@RequestBody Posts post){
        String response = postService.removePost(post);
        if(response != null){
            return ResponseEntity.ok()
                    .body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    @PutMapping("updatePost")
    public ResponseEntity<?> updatePost(@RequestBody Posts post){
        return postService.updatePost(post);
    }

    @GetMapping("findBySearch/{keyword}")
    public ResponseEntity<?> findBySearch(@PathVariable String keyword){
       return postService.findBySearch(keyword);
    }

    @GetMapping("findById/{post_id}")
    public ResponseEntity<?> findById(@PathVariable long post_id){
        return postService.findById(post_id);
    }

    @GetMapping("autherPost/{id}")
    public ResponseEntity<?> findByAuthorId(@PathVariable long id){
        return postService.findByAuthorId(id);
    }

    @DeleteMapping("removeAll/{author_id}")
    public ResponseEntity<?> removeAll(@PathVariable long author_id){
        return postService.removeAll(author_id);
    }

    @GetMapping("findByCategory/{id}")
    public ResponseEntity<?> findByCategory(@PathVariable long id){
        return postService.findByCategoryId(id);
    }

    @PostMapping("updateViewCount/{id}")
    public ResponseEntity<?> updateViewCount(@PathVariable long id){
        return postService.updateViewCount(id);
    }

}
