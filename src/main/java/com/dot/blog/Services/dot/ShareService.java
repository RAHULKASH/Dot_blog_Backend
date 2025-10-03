package com.dot.blog.Services.dot;

import com.dot.blog.DTO.SharesDto;
import com.dot.blog.Models.Shares;
import com.dot.blog.Repositories.dot.ShareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {

    @Autowired
    private ShareRepo shareRepo;

    public ResponseEntity<?> addShare(Shares share){
        try{
            Shares data= shareRepo.save(share);
            SharesDto sharesDto=new SharesDto(data.getId(),data.getPost().getId(),data.getUser().getId(),data.getPlatform(),data.getCreatedAt());
            return ResponseEntity.ok(sharesDto);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> countShare(long post_id){
        try{
            List<Shares> shares=shareRepo.findByPostId(post_id);
            if(shares.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Blog Found");
            }
            return ResponseEntity.ok(shares.size());
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
