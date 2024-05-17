package com.story.concho.controller.api;

import com.story.concho.model.domain.Post;
import com.story.concho.model.domain.Reply;
import com.story.concho.service.AwsS3Service;
import com.story.concho.service.ForumService;
import com.story.concho.service.ReplyService;
import com.story.concho.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class ApiForumController {
    private final ForumService forumService;
    private final ReplyService replyService;
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    @Autowired
    public ApiForumController(ForumService forumService, ReplyService replyService, UserService userService, AwsS3Service awsS3Service){
        this.forumService = forumService;
        this.replyService = replyService;
        this.userService = userService;
        this.awsS3Service = awsS3Service;
    }

    // 게시판 좋아요 증가
    @PostMapping("/increase-post-like")
    public Boolean increasePostLikeCnt(@RequestParam("forumId") int forumId, HttpSession session){
        if(session.getAttribute("email") == null) {
            if(!userService.emailCheckOk((String) session.getAttribute("email"))){
                System.out.println("세션인증 실패");
                return false;
            }
        }

        forumService.increaseLikeCount(forumId);
        return true;
    }
    // 댓글 좋아요 증가
    @PostMapping("/increase-reply-like")
    public Boolean increaseReplyLikeCnt(@RequestParam("replyId") int replyId, HttpSession session){
        if(session.getAttribute("email") == null) {
            if(!userService.emailCheckOk((String) session.getAttribute("email"))){
                System.out.println("세션인증 실패");
                return false;
            }
        }
        replyService.increaseLikeCount(replyId);
        return true;
    }
    // 댓글 달기
    @PostMapping("/create-reply")
    public Map<String, String> createReply(@RequestBody Map<String, String> jsonMap, HttpSession session){
        HashMap<String, String> resultJsonMap = new HashMap<>();
        if(session.getAttribute("email") == null || !userService.emailCheckOk((String) session.getAttribute("email"))) {
            System.out.println("세션인증 실패" + (String) session.getAttribute("email"));
            resultJsonMap.put("result", "false");
        }else{
            String email = (String) session.getAttribute("email");

            // ajax 받아오는 데이터
            String nickName = jsonMap.get("nickName");
            int forumId = Integer.parseInt(jsonMap.get("forumId"));
            String replyContent = jsonMap.get("replyContent");
            int replyDepth = Integer.parseInt(jsonMap.get("replyDepth"));
            int replyStep = Integer.parseInt(jsonMap.get("replyStep"));

            Reply reply = new Reply(
                    email, nickName, forumId, replyContent, replyDepth, replyStep
            );
            Reply replyOut = replyService.addReply(reply);

            if(replyOut != null){
                resultJsonMap.put("replyId", String.valueOf(replyOut.getReplyId()));
                resultJsonMap.put("result", "true");
            }else{
                resultJsonMap.put("result", "false");
            }
        }
        return resultJsonMap;
    }

    // 댓글 삭제
    @PostMapping("/delete-reply")
    public Boolean deleteReply(@RequestParam("replyId") int replyId, HttpSession session){
        if(session.getAttribute("email") == null) {
            if(!userService.emailCheckOk((String) session.getAttribute("email"))){
                System.out.println("세션인증 실패");
                return false;
            }
        }
        String email = (String)session.getAttribute("email");
        if(replyService.checkReplyEmail(replyId,email)){
            replyService.deleteReply(replyId);
            return true;
        }else{
            return false;
        }
    }

    //게시글 삭제
    @PostMapping("/delete-post")
    public Boolean deletePost(@RequestParam("id") int postId, HttpSession session){
        if(session.getAttribute("email") == null) {
            if(!userService.emailCheckOk((String) session.getAttribute("email"))){
                System.out.println("세션인증 실패");
                return false;
            }
        }
        Optional<Post> postOptional = forumService.getPostById(postId);
        if(postOptional.isPresent()){
            if(!postOptional.get().getFileKey().equals("story/nomal_1234.png")){
                awsS3Service.deleteFile(postOptional.get().getFileKey());
            }
            forumService.deletePostById(postId);
        }else{
            return false;
        }
        return true;
    }
}
