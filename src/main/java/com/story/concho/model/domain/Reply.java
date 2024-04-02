package com.story.concho.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reply_table")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private int replyId;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_nickname")
    private String nickName;
    @Column(name = "forum_id")
    private int forumId;
    @Column(name = "reply_content")
    private String replyContent;
    @Column(name = "reply_likes_count")
    private int replyLikesCount;
    @Column(name = "reply_depth")
    private int replyDepth;
    @Column(name = "reply_step")
    private int replyStep;
    @Column(name = "reply_date")
    private LocalDateTime replyDate;
    public Reply() {}
    public Reply(String email, String nickName, int forumId, String replyContent,int replyDepth, int replyStep) {
        this.email = email;
        this.nickName = nickName;
        this.forumId = forumId;
        this.replyContent = replyContent;
        this.replyDepth = replyDepth;
        this.replyStep = replyStep;
    }
}
