package com.dot.blog.Services.dot;

import com.dot.blog.DTO.PostsDTO;
import com.dot.blog.DTO.UsersDto;
import com.dot.blog.Models.Posts;
import com.dot.blog.Models.Tags;
import com.dot.blog.Models.Users;
import com.dot.blog.Repositories.dot.PostRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    private Set<String> getTagsNames(Set<Tags> tags){
        Set<String> tagNames=new HashSet<>();
        for(Tags item:tags){
            tagNames.add(item.getName());
        }
        return tagNames;
    }

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

    public ResponseEntity<?> addPost(Posts post) {
        try{
            if (postRepo.existsBySlug(post.getSlug())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Slug must be unique");
            }

            Posts data=postRepo.save(post);
            PostsDTO postsDTO=new PostsDTO(data.getId(),
                    data.getTitle(),
                    data.getContent(),
                    data.getSlug(),
                    getUser(data.getAuthor()),
                    getTagsNames(data.getTags()),
                    data.getCategory().getName(),
                    data.getViewCount(),
                    data.getCreatedAt(),
                    data.getUpdatedAt(),
                    data.getComments().size(),
                    data.getLikes().size(),
                    data.getShares().size());

            return ResponseEntity.ok(postsDTO);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public String removePost(Posts post){
        try{
            postRepo.delete(post);
            return "Post Removed ";
        }catch(Exception e){
            return "Error Occured";
        }
    }

    public ResponseEntity<?> findBySearch(String keyword){
        try{
            List<Posts> posts=postRepo.findByTitleContainingIgnoreCase(keyword);
            List<PostsDTO> postsDTOS=new ArrayList<>();
            for(Posts item:posts){
                postsDTOS.add(new PostsDTO(item.getId(),
                        item.getTitle(),
                        item.getContent(),
                        item.getSlug(),
                        getUser(item.getAuthor()),
                        getTagsNames(item.getTags()),
                        item.getCategory().getName(),
                        item.getViewCount(),
                        item.getCreatedAt(),
                        item.getUpdatedAt(),
                        item.getComments().size(),
                        item.getLikes().size(),
                        item.getShares().size())
                );
            }
            return ResponseEntity.ok(postsDTOS);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findById(long post_id){
        try{
            Posts data=postRepo.findById(post_id).orElse(null);
            if(data==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Blog Found");
            }
            PostsDTO postsDTO=new PostsDTO(data.getId(),
                    data.getTitle(),
                    data.getContent(),
                    data.getSlug(),
                    getUser(data.getAuthor()),
                    getTagsNames(data.getTags()),
                    data.getCategory().getName(),
                    data.getViewCount(),
                    data.getCreatedAt(),
                    data.getUpdatedAt(),
                    data.getComments().size(),
                    data.getLikes().size(),
                    data.getShares().size());

            return ResponseEntity.ok(postsDTO);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findByAuthorId(long authorId) {
        try {
            List<Posts> posts = postRepo.findByAuthorId(authorId);

            List<PostsDTO> dto= posts.stream().map(item -> new PostsDTO(
                    item.getId(),
                    item.getTitle(),
                    item.getContent(),
                    item.getSlug(),
                    getUser(item.getAuthor()),
                    getTagsNames(item.getTags()),
                    item.getCategory().getName(),
                    item.getViewCount(),
                    item.getCreatedAt(),
                    item.getUpdatedAt(),
                    item.getComments().size(),
                    item.getLikes().size(),
                    item.getShares().size()
            )).toList();

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> updatePost(Posts post){
        try{
            Posts data=postRepo.save(post);
            PostsDTO postsDTO=new PostsDTO(data.getId(),
                    data.getTitle(),
                    data.getContent(),
                    data.getSlug(),
                    getUser(data.getAuthor()),
                    getTagsNames(data.getTags()),
                    data.getCategory().getName(),
                    data.getViewCount(),
                    data.getCreatedAt(),
                    data.getUpdatedAt(),
                    data.getComments().size(),
                    data.getLikes().size(),
                    data.getShares().size());

            return ResponseEntity.ok(postsDTO);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> removeAll(long author_id){
        try{
            postRepo.deleteAllByAuthorId(author_id);
            return ResponseEntity.ok("All Post removed") ;
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findPopular() {
        try{
            List<Posts> posts= postRepo.findPopular();

            List<PostsDTO> postsDTOS=new ArrayList<>();
            for(Posts item:posts){
                postsDTOS.add(new PostsDTO(item.getId(),
                        item.getTitle(),
                        item.getContent(),
                        item.getSlug(),
                        getUser(item.getAuthor()),
                        getTagsNames(item.getTags()),
                        item.getCategory().getName(),
                        item.getViewCount(),
                        item.getCreatedAt(),
                        item.getUpdatedAt(),
                        item.getComments().size(),
                        item.getLikes().size(),
                        item.getShares().size())
                );
            }
            return ResponseEntity.ok(postsDTOS);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findByCategoryId(long id) {
        try{
            List<Posts> posts=postRepo.findByCategoryId(id);
            if(posts.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Blog Found");
            }
            List<PostsDTO> postsDTOS=new ArrayList<>();
            for(Posts item:posts){
                postsDTOS.add(new PostsDTO(item.getId(),
                        item.getTitle(),
                        item.getContent(),
                        item.getSlug(),
                        getUser(item.getAuthor()),
                        getTagsNames(item.getTags()),
                        item.getCategory().getName(),
                        item.getViewCount(),
                        item.getCreatedAt(),
                        item.getUpdatedAt(),
                        item.getComments().size(),
                        item.getLikes().size(),
                        item.getShares().size())
                );
            }
            return ResponseEntity.ok(postsDTOS);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    public ResponseEntity<?> updateViewCount(long id) {
        try {
            Optional<Posts> optionalPost = postRepo.findById(id);
            if (optionalPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Post not found");
            }

            Posts post = optionalPost.get();
            // increment view count
            post.setViewCount(post.getViewCount() + 1);

            // save updated post
            postRepo.save(post);

            return ResponseEntity.ok("View count updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating view count: " + e.getMessage());
        }
    }
}
