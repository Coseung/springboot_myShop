package com.myShop.myShop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @PostMapping("/Commentadd")
    String Commentadd(@RequestParam String comment, @RequestParam String username,@RequestParam Long id, Model model) {
        commentService.commentsave(comment,username,id);

        return "redirect:/detail/"+id;
    }

}
