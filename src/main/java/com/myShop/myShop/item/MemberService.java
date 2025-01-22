package com.myShop.myShop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void regist(@RequestParam String username,@RequestParam String displayName, @RequestParam String password) {
        Member member = new Member();
        member.setUsername(username);
        var result = new BCryptPasswordEncoder().encode(password);
        member.setPassword(result);
        member.setDisplayName(displayName);
        memberRepository.save(member);

    }
}
