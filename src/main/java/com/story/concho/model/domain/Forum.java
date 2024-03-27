package com.story.concho.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="forum_table")
public class Forum {
    @Id
    @Column(name="forum_id")
    int id;
    @Column(name="forum_title")
    String title;
    @Column(name="forum_content")
    String content;
    @Column(name = "forum_author_email")
    String authorEmail;
    @Column(name="forum_date")
    String date;
    @Column(name = "forum_updated_date")
    String updatedDate;
    @Column(name = "forum_view_count")
    int viewCount;
    @Column(name="forum_comment_count")
    int commentCount;
    @Column(name = "forum_like_count")
    int likeCount;

}
