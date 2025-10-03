package com.dot.blog.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostsDTO {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private UsersDto author; // Include basic user info, or just authorId if preferred
    private Set<String> tagNames; // Instead of full Tag objects
    private String categoryName; // Instead of full Category object
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Optionally include counts instead of full lists
    private int commentCount;
    private int likeCount;
    private int shareCount;

    public PostsDTO(Long id, String title, String content, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
    }
}
