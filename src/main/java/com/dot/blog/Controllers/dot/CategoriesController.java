package com.dot.blog.Controllers.dot;

import com.dot.blog.DTO.CategoriesDto;
import com.dot.blog.Models.Categories;
import com.dot.blog.Services.dot.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/categories")
@RestController
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("addCategory")
    public ResponseEntity<CategoriesDto> addCategories(@RequestBody Categories category){
        CategoriesDto response = categoriesService.addCategory(category);
        if(response != null){
            return ResponseEntity.ok()
                    .body(response);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @DeleteMapping("removeCategory")
    public ResponseEntity<String> removeCategory(@RequestBody Categories category){
        String response=categoriesService.removeCategory(category);
        if(response !=null){
            return ResponseEntity.ok()
                    .body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
       return categoriesService.findAll();
    }
}
