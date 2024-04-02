package com.story.concho.model.repository;

import com.story.concho.model.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForumRepository extends CrudRepository<Post, Integer> {
    // 해당 페이지의 게시글들을 가져오는 쿼리
    @Query("SELECT f FROM Post f ORDER BY f.date DESC ")
    Page<Post> findPageForumPosts(Pageable pageable);

}
