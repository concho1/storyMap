package com.story.concho.service;

import com.story.concho.model.domain.Reply;
import com.story.concho.model.repository.ForumRepository;
import com.story.concho.model.repository.ImgRepository;
import com.story.concho.model.repository.ReplyRepository;
import com.story.concho.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {
    private final UserRepository userRepository;
    private final ImgRepository imgRepository;
    private final ForumRepository forumRepository;
    private final ReplyRepository replyRepository;

    // 의존성 주입
    @Autowired
    public ReplyService(ReplyRepository replyRepository, UserRepository userRepository, ImgRepository imgRepository, ForumRepository forumRepository){
        this.userRepository = userRepository;
        this.imgRepository = imgRepository;
        this.forumRepository = forumRepository;
        this.replyRepository = replyRepository;
    }

    // 댓글 추가
    public Reply addReply(Reply reply) {
        // 현재 날짜와 시간을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        reply.setReplyDate(now);
        reply.setReplyLikesCount(0);
        replyRepository.incrementReplyStepByForumIdAndGreaterOrEqualStep(reply.getForumId(), reply.getReplyStep());
        Reply replyOut = replyRepository.save(reply);
        return replyOut;
    }
    public boolean checkReplyEmail(int replyId, String email){
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        if(replyOptional.isPresent()){
            Reply reply = replyOptional.get();
            System.out.println(reply.getEmail());
            System.out.println(email);
            if(reply.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
    // 댓글 삭제 (수정으로 처리)
    public void deleteReply(Integer replyId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        if(replyOptional.isPresent()){
            Reply reply = replyOptional.get();
            reply.setReplyContent("삭제된 댓글입니다.");
            reply.setNickName("");
            reply.setReplyDate(null);
            reply.setReplyLikesCount(0);
            replyRepository.save(reply);
        }else{
            System.out.println("댓글 삭제 오류 id 없음");
        }
    }

    public void increaseLikeCount(Integer replyId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        if(replyOptional.isPresent()){
            Reply reply = replyOptional.get();

            reply.setReplyLikesCount(reply.getReplyLikesCount()+1);
            replyRepository.save(reply);
        }else{
            System.out.println("댓글 삭제 오류 id 없음");
        }
    }

    // 특정 포럼 ID에 대한 댓글 리스트 가져오기
    public List<Reply> getRepliesByForumId(int forumId) {
        List<Reply> replyList = replyRepository.findByForumIdOrderByReplyStep(forumId);
        return replyList;
    }
}
