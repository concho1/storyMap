package com.story.concho.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name="forum_table")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="forum_id")
    private Integer id;
    @Column(name="forum_title")
    private String title;
    @Column(name="forum_content")
    private String content;
    @Column(name = "forum_author_email")
    private String authorEmail;
    @Column(name="forum_date")
    private LocalDateTime date;
    @Column(name = "forum_updated_date")
    private String updatedDate;
    @Column(name = "forum_view_count")
    private int viewCount;
    @Column(name="forum_comment_count")
    private int commentCount;
    @Column(name = "forum_like_count")
    private int likeCount;
    @Column(name="forum_img_path")
    private String path;


    public Post() {

    }
    public Post(String title, String content, String authorEmail, int viewCount, int commentCount, int likeCount, String path) {
        this.title = title;
        this.content = content;
        this.authorEmail = authorEmail;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.path = path;
    }
}
