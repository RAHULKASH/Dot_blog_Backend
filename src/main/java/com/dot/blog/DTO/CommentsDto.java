package com.dot.blog.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private Long id;
    private String content;
    private UsersDto author; // Or just authorId
    private Long postId; // Reference by ID only
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
