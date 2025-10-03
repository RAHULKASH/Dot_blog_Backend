package com.dot.blog.Services.dot;

import com.dot.blog.DTO.CategoriesDto;
import com.dot.blog.Models.Categories;
import com.dot.blog.Repositories.dot.CategoriesRepo;
import jdk.jfr.Category;
import jdk.jfr.RecordingState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesService {

    @Autowired
    private CategoriesRepo categoriesRepo;

    public CategoriesDto addCategory(Categories category) {
        try {
            Categories response= categoriesRepo.save(category);
            CategoriesDto categoriesDto=new CategoriesDto(response.getId(),
                    response.getName(),response.getDescription(),response.getPosts().size());
            return categoriesDto;
        }catch(Exception e){
            return null;
        }
    }

    public String removeCategory(Categories category) {
        try{
             categoriesRepo.delete(category);
             return "Category Removed";
        }catch (Exception e){
            return null;
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<Categories> categories = categoriesRepo.findAll();

            if (categories.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("No categories found");
            }

            List<CategoriesDto> categoriesDtos = new ArrayList<>();
            for (Categories item : categories) {
                categoriesDtos.add(new CategoriesDto(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPosts().size()
                ));
            }

            return ResponseEntity.ok(categoriesDtos);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
