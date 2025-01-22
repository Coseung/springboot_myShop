package com.myShop.myShop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    //댓글 기능
    public void commentsave (String comment, String username, Long id) {
        var Obj = new Comment();
        Obj.setUsername(username);
        Obj.setParentId(id);
        Obj.setContent(comment);
        commentRepository.save(Obj);
    }
}
