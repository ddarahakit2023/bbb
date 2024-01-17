package com.woof.api.member.service;

import com.woof.api.member.model.Member;
import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostMemberLoginReq;
import com.woof.api.member.model.requestdto.PostMemberSignupReq;
import com.woof.api.member.model.responsedto.PostMemberLoginRes;
import com.woof.api.member.model.responsedto.PostMemberSignupRes;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor        // 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).get();
    }


    // Member CRUD

    // create

    // client에게 repository에 저장할 정보를 요청
    // 응답으로 반환
    public PostMemberSignupRes signup(PostMemberSignupReq postMemberSignupReq) {
        // 멤버 정보를 빌드로 저장
        Member member = Member.builder()
                .email(postMemberSignupReq.getEmail())
                .password(passwordEncoder.encode(postMemberSignupReq.getPassword()))
                .nickname(postMemberSignupReq.getNickname())
                .authority("ROLE_MEMBER")
                .status(false)
                .build();

        // 레포지토리에 저장 -> id 생성
        memberRepository.save(member);

        Map<String, Long> result = new HashMap<>();
        result.put("idx", member.getIdx());


        // 응답 형식
        PostMemberSignupRes postMemberSignupRes = PostMemberSignupRes.builder()
                .isSuccess(true)
                .code(1000L)
                .message("요청 성공.")
                .result(result)
                .success(true)
                .build();

        return postMemberSignupRes;
    }


    public Boolean getCheckEmail(GetEmailConfirmReq getEmailConfirmReq) {
        Optional<Member> result = memberRepository.findByEmail(getEmailConfirmReq.getEmail());
        // 레포지토리에 존재하지 않는다면 true 반환
        if (!result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Member> result = memberRepository.findByEmail(username);
        Member member = null;
        if(result.isPresent()) {
            member = result.get();
        }

        return member;
    }
    // read


}