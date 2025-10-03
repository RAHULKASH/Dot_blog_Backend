package com.dot.blog.Services.dot;

import com.dot.blog.DTO.LikesDto;
import com.dot.blog.DTO.PostsDTO;
import com.dot.blog.DTO.UsersDto;
import com.dot.blog.Models.Likes;
import com.dot.blog.Models.Posts;
import com.dot.blog.Models.Tags;
import com.dot.blog.Models.Users;
import com.dot.blog.Repositories.dot.LikeRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

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

    public ResponseEntity<?> addLike(Likes like) {
        try{
            Likes data=likeRepo.save(like);
            LikesDto likesDto=new LikesDto(data.getId(),data.getUser().getId(),data.getPost().getId(),data.getCreatedAt());
            return ResponseEntity.ok(likesDto);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public String removeLike(long userId, long postId){
        Optional<Likes> like = likeRepo.findByPostIdAndUserId(postId,userId);
        if (like.isPresent()) {
            likeRepo.delete(like.get());
            return "Like removed successfully!";
        }
        return "Like not found!";
    }

    public ResponseEntity<Boolean> isLiked(long userId, long postId){
        Optional<Likes> like = likeRepo.findByPostIdAndUserId(postId,userId);
        if (like.isPresent()) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    public ResponseEntity<?> findAllByUserId(Long userId) {
        try {
            List<Likes> likes = likeRepo.findAllByUserId(userId);
            List<PostsDTO> likedPosts = new ArrayList<>();
            for (Likes item : likes) {
                Posts post = item.getPost();
                likedPosts.add(new PostsDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getSlug(),
                        getUser(post.getAuthor()),
                        getTagsNames(post.getTags()),
                        post.getCategory().getName(),
                        post.getViewCount(),
                        post.getCreatedAt(),
                        post.getUpdatedAt(),
                        post.getComments().size(),
                        post.getLikes().size(),
                        post.getShares().size()
                ));
            }
            return ResponseEntity.ok(likedPosts);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findAllByPostId(Long post_id){
        try{
            List<Likes> likes=likeRepo.findAllByPostId(post_id);
            if(likes.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Liked blogs are not available");
            }
            List<LikesDto> likesDtos=new ArrayList<>();
            for(Likes item:likes){
                likesDtos.add(new LikesDto(item.getId(),item.getUser().getId(),item.getPost().getId(),item.getCreatedAt()));
            }
            return ResponseEntity.ok(likesDtos);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
