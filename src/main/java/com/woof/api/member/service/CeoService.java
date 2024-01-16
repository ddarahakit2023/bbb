package com.woof.api.member.service;

import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostCeoSignupReq;
import com.woof.api.member.model.responsedto.PostCeoSignupRes;
import com.woof.api.member.repository.CeoRepository;
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
public class CeoService implements UserDetailsService {
    private final CeoRepository ceoRepository;
    private final PasswordEncoder passwordEncoder;

    public Ceo getMemberByEmail(String email) {
        return ceoRepository.findByEmail(email).get();
    }

    // Member CRUD

    // create

    // client에게 repository에 저장할 정보를 요청
    // 응답으로 반환
    public PostCeoSignupRes signup(PostCeoSignupReq postCeoSignupReq) {
        // 멤버 정보를 빌드로 저장
        Ceo ceo = Ceo.builder()
                .email(postCeoSignupReq.getEmail())
                .password(passwordEncoder.encode(postCeoSignupReq.getPassword()))
                .nickname(postCeoSignupReq.getNickname())
                .authority("ROLE_CEO")
                .status(false)
                .build();

        // 레포지토리에 저장 -> id 생성
        ceoRepository.save(ceo);

        Map<String, Long> result = new HashMap<>();
        result.put("idx", ceo.getIdx());

        // 응답 형식
        PostCeoSignupRes postCeoSignupRes = PostCeoSignupRes.builder()
                .isSuccess(true)
                .code(1000L)
                .message("요청 성공.")
                .result(result)
                .success(true)
                .build();

        return postCeoSignupRes;
    }

    public Boolean getCheckEmail(GetEmailConfirmReq getEmailConfirmReq) {
        Optional<Ceo> result = ceoRepository.findByEmail(getEmailConfirmReq.getEmail());
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
}