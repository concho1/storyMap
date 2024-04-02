package com.story.concho.controller.web.forum;

import com.story.concho.model.domain.Post;
import com.story.concho.model.domain.Reply;
import com.story.concho.model.domain.User;
import com.story.concho.service.ForumService;
import com.story.concho.service.ImgService;
import com.story.concho.service.ReplyService;
import com.story.concho.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class ForumController {
    private final UserService userService;
    private final ImgService imgService;
    private final ForumService forumService;
    private final ReplyService replyService;
    // 생성자 의존성 주입
    @Autowired
    public ForumController(ForumService forumService, UserService userService, ImgService imgService, ReplyService replyService){
        this.userService = userService;
        this.imgService = imgService;
        this.forumService = forumService;
        this.replyService = replyService;
    }
    public static boolean sessionCheck(HttpSession session){
        return (session.getAttribute("email") == null);
    }
    @GetMapping("/forumUpload")
    public String getFoumUpload(){
        return "pages/user/post";
    }

    @GetMapping("/post")
    public String getPost(Model model){
        model.addAttribute("post", new Post());
        return "pages/user/post";
    }

    @GetMapping("/post-info")
    public String getPostInfoPage(
            @RequestParam(name = "postId", defaultValue = "1") int postId,
            @RequestParam(name="pageNum", defaultValue = "0") int pageNum,
            Model model, HttpSession session, RedirectAttributes redirectAttributes){
        if(sessionCheck(session)){
            redirectAttributes.addFlashAttribute("message", "세션이 만료되었습니다.");
            return  "redirect:/user/log-in-form";
        }

        String userEmail = (String)session.getAttribute("email");
        Optional<Post> postOptional = forumService.getPostById(postId);
        if(postOptional.isPresent()){
            Post outPost = postOptional.get();
            forumService.increaseViewCount(outPost.getId());
            outPost.setViewCount(outPost.getViewCount() + 1);

            String email = userService.getNickNameByEmail(outPost.getAuthorEmail());
            String userNickName = userService.getNickNameByEmail(userEmail);
            List<Reply> replyList = replyService.getRepliesByForumId(outPost.getId());
            for(Reply reply : replyList){
                System.out.println(reply.getNickName());
            }

            model.addAttribute("replyList", replyList);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("post", outPost);
            model.addAttribute("nickName", email);
            model.addAttribute("userNickName", userNickName);
        }else{
            redirectAttributes.addFlashAttribute("message", "게시글 정보가 없습니다.");
            return "redirect:/user/forum";
        }
        return "pages/user/postInfo";
    }

    @GetMapping("/update-post")
    public String upDatePost(@RequestParam(name = "postId", defaultValue = "1") int postId,
                             @RequestParam(name="pageNum", defaultValue = "0") int pageNum,
                             Model model, HttpSession session, RedirectAttributes redirectAttributes){
        if(sessionCheck(session)){
            redirectAttributes.addFlashAttribute("message", "세션이 만료되었습니다.");
            return  "redirect:/user/log-in-form";
        }
        String email = (String)session.getAttribute("email");
        Optional<Post> postOptional = forumService.getPostById(postId);
        if(postOptional.isPresent()){
            Post outPost = postOptional.get();
            String nickName = userService.getNickNameByEmail(outPost.getAuthorEmail());
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("post", outPost);
            model.addAttribute("nickName", nickName);

            // 업데이트 하려는 사용자가 글쓴이와 일치하는지 확인
            if(outPost.getAuthorEmail().equals(email)){
                return "pages/user/postUpdate";
            }else{
                model.addAttribute("message", "작성자만 수정할 수 있습니다.");
                //redirectAttributes.addFlashAttribute("message", "작성자만 수정할 수 있습니다.");
                return "pages/user/postInfo";
            }
        }else{
            redirectAttributes.addFlashAttribute("message", "게시글 정보가 없습니다.");
            return "redirect:/user/forum";
        }
    }
    @GetMapping("/forum")
    public String getForum(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpSession session, RedirectAttributes redirectAttributes){
        if(sessionCheck(session)){
            redirectAttributes.addFlashAttribute("message", "세션이 만료되었습니다.");
            return  "redirect:/user/log-in-form";
        }

        int size = 6;
        List<Post> postList = forumService.getPageNumPosts(page, size);
        List<String> dateList = new LinkedList<>();
        for(Post postTm : postList){
            String dateTm = String.valueOf(postTm.getDate());
            dateTm = dateTm.replaceAll("T", " 시간 : ");
            dateTm = "날짜 : " + dateTm;
            dateList.add(dateTm);
        }
        int maxPage = forumService.getCntPost(size);

        model.addAttribute("maxPage", maxPage);
        model.addAttribute("page", page);
        model.addAttribute("postList", postList);
        model.addAttribute("dateList", dateList);
        return "pages/user/forumUser";
    }

    @PostMapping("/forum-post")
    public String forumPost(HttpSession session, @ModelAttribute Post post, RedirectAttributes redirectAttributes){
        if(sessionCheck(session)){
            redirectAttributes.addFlashAttribute("message", "세션이 만료되었습니다.");
            return  "redirect:/user/log-in-form";
        }
        post.setAuthorEmail((String) session.getAttribute("email"));
        if(forumService.uploadPost(post)){
            // 성공
            redirectAttributes.addFlashAttribute("message", "게시글 작성 성공");
            System.out.println("게시글 업로드 성공");
            return "redirect:/user/forum";

        }else{
            // 실패
            redirectAttributes.addFlashAttribute("message", "게시글 작성 실패");
            System.out.println("게시글 업로드 실패");
            return "redirect:/user/post";
        }

    }


}
