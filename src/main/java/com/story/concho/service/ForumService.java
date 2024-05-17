package com.story.concho.service;

import com.story.concho.model.domain.Post;
import com.story.concho.model.repository.ForumRepository;
import com.story.concho.model.repository.ImgRepository;
import com.story.concho.model.repository.ReplyRepository;
import com.story.concho.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ForumService {
    private final AwsS3Service awsS3Service;
    private final ImgService imgService;
    private final ForumRepository forumRepository;
    private final ReplyRepository replyRepository;

    // 의존성 주입
    @Autowired
    public ForumService(AwsS3Service awsS3Service, ImgService imgService, ForumRepository forumRepository, ReplyRepository replyRepository){
        this.awsS3Service = awsS3Service;
        this.imgService = imgService;
        this.forumRepository = forumRepository;
        this.replyRepository = replyRepository;
    }

    public boolean uploadPost(Post post){
        // 받아오는 값 title, content, email, path
        // title, content, authorEmail, viewCount=0, commentCount=0, likeCount=0
        try{
            post.setViewCount(0);
            post.setCommentCount(0);
            post.setLikeCount(0);

            // 현재 날짜와 시간을 가져옵니다.
            LocalDateTime now = LocalDateTime.now();
            post.setDate(now);
            if(post.getFileKey().isEmpty()){
                post.setFileKey("story/nomal_1234.png");
            }
            forumRepository.save(post);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Post> getPageNumPosts(int pageNum, int size){
        PageRequest pageRequest = PageRequest.of(pageNum, size);
        Page<Post> postPages = forumRepository.findPageForumPosts(pageRequest);
        List<Post> postList = postPages.getContent();

        for(Post post : postList){
            post.setPath(
                awsS3Service.getUrl(post.getFileKey())
            );
        }

        return  postList;
    }

    public int getCntPost(int size){
        long postCnt = forumRepository.count();
        int maxPage;

        if(postCnt % size == 0) maxPage = (int)(postCnt/size);
        else maxPage = (int) (postCnt / size) +1;
        System.out.println(maxPage);
        return maxPage;
    }

    public Optional<Post> getPostById(int id){
        Optional<Post> postOptional = forumRepository.findById(id);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            post.setPath(
                    awsS3Service.getUrl(post.getFileKey())
            );
            postOptional = Optional.of(post);
        }
        return  postOptional;
    }

    // 좋아요 증가
    public void increaseLikeCount(int forumId) {
        Optional<Post> postOptional = forumRepository.findById(forumId);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            post.setLikeCount(post.getLikeCount() + 1);
            forumRepository.save(post);
        }else{
            System.out.println("게시판 아이디 없음 오류");
        }
    }

    // 조회수 증가
    public void increaseViewCount(int forumId) {
        Optional<Post> postOptional = forumRepository.findById(forumId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setViewCount(post.getViewCount() + 1);
            forumRepository.save(post);
        }else{
            System.out.println("게시판 아이디 없음 오류");
        }
    }

    // 게시판 id로 글 삭제(댓글까지)
    @Transactional
    public void deletePostById(int forumId) {
        replyRepository.deleteAllByForumId(forumId);
        forumRepository.deleteById(forumId);
    }
}
