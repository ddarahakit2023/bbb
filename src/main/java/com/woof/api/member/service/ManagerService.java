package com.woof.api.member.service;

import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostManagerSignupReq;
import com.woof.api.member.model.responsedto.PostManagerSignupRes;
import com.woof.api.member.repository.ManagerRepository;
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
public class ManagerService implements UserDetailsService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public Manager getMemberByEmail(String email) {
        return managerRepository.findByEmail(email).get();
    }

    // Member CRUD

    // create

    // client에게 repository에 저장할 정보를 요청
    // 응답으로 반환
    public PostManagerSignupRes signup(PostManagerSignupReq postManagerSignupReq) {
        // 멤버 정보를 빌드로 저장
        Manager manager = Manager.builder()
                .email(postManagerSignupReq.getEmail())
                .password(passwordEncoder.encode(postManagerSignupReq.getPassword()))
                .nickname(postManagerSignupReq.getNickname())
                .authority("ROLE_MANAGER")
                .status(false)
                .build();

        // 레포지토리에 저장 -> id 생성
        managerRepository.save(manager);

        Map<String, Long> result = new HashMap<>();
        result.put("idx", manager.getIdx());

        // 응답 형식
        PostManagerSignupRes postManagerSignupRes = PostManagerSignupRes.builder()
                .isSuccess(true)
                .code(1000L)
                .message("요청 성공.")
                .result(result)
                .success(true)
                .build();

        return postManagerSignupRes;
    }

    public Boolean getCheckEmail(GetEmailConfirmReq getEmailConfirmReq) {
        Optional<Manager> result = managerRepository.findByEmail(getEmailConfirmReq.getEmail());
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