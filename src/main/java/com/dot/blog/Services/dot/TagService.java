package com.dot.blog.Services.dot;

import com.dot.blog.DTO.TagsDto;
import com.dot.blog.Models.Tags;
import com.dot.blog.Repositories.dot.TagsRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagsRepo tagsRepo;

    public ResponseEntity<?> addTag(Tags tag){
        try{
            Tags data= tagsRepo.save(tag);
            TagsDto tagsDto=new TagsDto(data.getId(),data.getName(),data.getPosts().size());
            return ResponseEntity.ok(data);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findAll(){
        try{
            List<Tags> tags=tagsRepo.findAll();
            if(tags.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Tags Found");
            }
            List<TagsDto> tagsDtos=new ArrayList<>();
            for(Tags item:tags){
                tagsDtos.add(new TagsDto(item.getId(),item.getName(),item.getPosts().size()));
            }
            return ResponseEntity.ok(tagsDtos);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> removeTag( Tags tag){
        try{
            tagsRepo.delete(tag);
            return ResponseEntity.ok("Tag removed");
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
