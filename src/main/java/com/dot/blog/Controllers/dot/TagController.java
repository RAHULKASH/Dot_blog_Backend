package com.dot.blog.Controllers.dot;

import com.dot.blog.DTO.TagsDto;
import com.dot.blog.Models.Tags;
import com.dot.blog.Services.dot.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("addTag")
    public ResponseEntity<?> addTag(@RequestBody Tags tag){
        return tagService.addTag(tag);
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return tagService.findAll();
    }

    @DeleteMapping("removeTag")
    public ResponseEntity<?> removeTag(@RequestBody Tags tag){
        return tagService.removeTag(tag);

    }
}
