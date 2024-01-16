package com.woof.api.member.service;


import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.requestdto.PostCeoSignupReq;
import com.woof.api.member.model.requestdto.PostManagerSignupReq;
import com.woof.api.member.model.responsedto.PostCeoSignupRes;
import com.woof.api.member.model.responsedto.PostManagerSignupRes;
import com.woof.api.member.repository.CeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CeoService {
    private final CeoRepository ceoRepository;
    private final PasswordEncoder passwordEncoder;

    public PostCeoSignupRes signup(PostCeoSignupReq postCeoSignupReq) {
       Ceo ceo = Ceo.builder()
               .businessnum(postCeoSignupReq.getBusinessnum())
               .password(passwordEncoder.encode(postCeoSignupReq.getPassword()))
               .email(postCeoSignupReq.getEmail())
               .authority("ROLE_CEO")
               .status(true)
               .build();

        ceoRepository.save(ceo);

        Map<String, Long> result = new HashMap<>();
        result.put("idx", ceo.getIdx());

        PostCeoSignupRes postCeoSignupRes = PostCeoSignupRes.builder()
                .isSuccess(true)
                .code(1000L)
                .message("요청 성공.")
                .result(result)
                .success(true)
                .build();

        return postCeoSignupRes;
    }
}
