package com.woof.api.member.service;

import com.woof.api.member.jwt.utils.TokenProvider;
import com.woof.api.member.model.EmailVerify;
import com.woof.api.member.model.Member;
import com.woof.api.member.repository.EmailVerifyRepository;
import com.woof.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final EmailVerifyRepository emailVerifyRepository;
    private final TokenProvider tokenProvider;
    private final JavaMailSender emailSender;
    private final MemberRepository memberRepository;
    // 순환참조 발생;; 멍청
//    private final EmailVerifyService emailVerifyService;

    // token = uuid, jwt = token
    public Boolean confirm(String email, String uuid){
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(email);

        // 이메일 존재하고 디비에 저장된 uuid 비교해서 같으면 true 반환
        if(result.isPresent()) {
            EmailVerify emailVerify = result.get();

            // emailVerify.getUuid() DB에 저장되어있는 uuid
            // uuid 인증 이메일에 적혀진 uuid
            // jwt는 db에 저장하지 않기 때문에 비교불가
            if(emailVerify.getUuid().equals(uuid)) {
                Optional<Member> member = memberRepository.findByEmail(email);
                if (member.isPresent()) {
                    member.get().setStatus(1);
                    memberRepository.save(member.get());
                    return true;
                }
            }
        }
        return false;

    }

    public void create(String email, String uuid) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();

        emailVerifyRepository.save(emailVerify);

    }

    public void sendMail (String email){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증");
        // uuid 생성
        String uuid = UUID.randomUUID().toString();
        create(email,uuid);
        // jwt 생성
        String jwt = TokenProvider.generateAccessToken(email, tokenProvider.getSecretKey(), tokenProvider.getExpiredTimeMs());
        message.setText("http://localhost:8080/member/confirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        emailSender.send(message);
    }

    public void update(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            Member member = result.get();
            member.setStatus(1);
            memberRepository.save(member);
        }
    }
}

