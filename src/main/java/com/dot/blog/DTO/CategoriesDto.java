package com.dot.blog.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDto {
    private Long id;
    private String name;
    private String description;

    // Optionally include count instead of full list
    private int postCount;

//    public CategoriesDto(Long id, String name, String description, int size) {
//    }
}
