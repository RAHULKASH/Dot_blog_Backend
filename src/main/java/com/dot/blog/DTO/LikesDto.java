package com.dot.blog.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {
    private Long id;
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;


}
