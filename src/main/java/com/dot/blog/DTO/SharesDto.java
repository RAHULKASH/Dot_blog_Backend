package com.dot.blog.DTO;

import com.dot.blog.ENUM.SharePlatform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharesDto {
    private Long id;
    private Long userId;
    private Long postId;
    private SharePlatform platform;
    private LocalDateTime createdAt;
}
