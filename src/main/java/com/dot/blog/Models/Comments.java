package com.dot.blog.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Users author;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comments(String content, Users user, Posts post) {
        this.content=content;
        this.author=user;
        this.post=post;
    }


    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }


    @PreUpdate
    protected void onUpdate() { this.updatedAt = LocalDateTime.now(); }



}
