package com.myShop.myShop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final MemberService memberService;

    @GetMapping("/regist")
    String regist(Model model) {
        return "regist.html";
    }

    @PostMapping("/signup")
    String signup(@RequestParam String username, @RequestParam String displayName, @RequestParam String password) {
        memberService.regist(username, displayName, password);
        System.out.println(username);
        System.out.println(displayName);
        System.out.println(password);
        return "redirect:/list";
    }
    @GetMapping("/login")
    String login(Model model) {
        return "login.html";
    }
    @GetMapping("/my-page")
    String myPage(Authentication authentication, Model model) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        System.out.println(user.displayName);
        return "mypage.html";
    }
}
