package com.story.concho.model.repository;

import com.story.concho.model.domain.Reply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Integer> {
    List<Reply> findByForumIdOrderByReplyStepDescReplyDateDesc(int forumId);
    List<Reply> findByForumIdOrderByReplyStep(int forumId);
    void deleteAllByForumId(int forumId);
    @Modifying
    @Transactional
    @Query("UPDATE Reply r SET r.replyStep = r.replyStep + 1 WHERE r.forumId = :forumId")
    void incrementReplyStepByForumId(int forumId);

    @Modifying
    @Transactional
    @Query("UPDATE Reply r SET r.replyStep = r.replyStep + 1 WHERE r.forumId = :forumId AND r.replyStep >= :replyStep")
    void incrementReplyStepByForumIdAndGreaterOrEqualStep(@Param("forumId") int forumId, @Param("replyStep") int replyStep);

}
