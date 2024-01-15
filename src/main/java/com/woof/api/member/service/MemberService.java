package com.woof.api.member.service;

import com.woof.api.member.model.Member;
import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostMemberReq;
import com.woof.api.member.model.responsedto.PostMemberRes;
import com.woof.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor        // 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMemberByEmail (String email){
        return memberRepository.findByEmail(email).get();
    }

    // Member CRUD

    // create

    // client에게 repository에 저장할 정보를 요청
    // 응답으로 반환
    public PostMemberRes signup(PostMemberReq postMemberReq){
        // 멤버 정보를 빌드로 저장
        Member member = Member.builder()
                .email(postMemberReq.getEmail())
                .password(passwordEncoder.encode(postMemberReq.getPassword()))
                .nickname(postMemberReq.getNickname())
                .authority("ROLE_USER")
                .status(0)
                .build();

        // 레포지토리에 저장 -> id 생성
        memberRepository.save(member);

        Map<String, Integer> result = new HashMap<>();
        result.put("id", member.getId());
        result.put("status", member.getStatus());

        // 응답 형식
        PostMemberRes postMemberRes = PostMemberRes.builder()
                .isSuccess(true)
                .code(1000)
                .message("요청 성공.")
                .result(result)
                .success(true)
                .build();

        return postMemberRes;
    }

    public Boolean getCheckEmail (GetEmailConfirmReq getEmailConfirmReq) {
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
        return null;
    }

    // read


}