package com.dot.blog.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagsDto {
    private Long id;
    private String name;

    // Optionally include post count
    private int postCount;
}
