package com.dot.blog.Controllers.dot;

import com.dot.blog.DTO.SharesDto;
import com.dot.blog.Models.Shares;
import com.dot.blog.Services.dot.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/shares/")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @PostMapping("addShare")
    public ResponseEntity<?> addShare(@RequestBody Shares share){
        return shareService.addShare(share);
    }

    @GetMapping("shareCount")
    public ResponseEntity<?> countShare(@PathVariable int post_id){
        return shareService.countShare(post_id);
    }

}
