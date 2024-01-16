package com.woof.api.member.service;

import com.woof.api.member.model.entity.EmailVerify;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.repository.MemberEmailVerifyRepository;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberEmailVerifyService {

    private final MemberEmailVerifyRepository memberEmailVerifyRepository;
    private final TokenProvider tokenProvider;
    private final JavaMailSender emailSender;
    private final MemberRepository memberRepository;
    public Boolean confirm(String email, String uuid){
        Optional<EmailVerify> result = memberEmailVerifyRepository.findByEmail(email);

        if(result.isPresent()) {
            EmailVerify emailVerify = result.get();

            if(emailVerify.getUuid().equals(uuid)) {
                Optional<Member> member = memberRepository.findByEmail(email);
                if (member.isPresent()) {
                    member.get().setStatus(true);
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

        memberEmailVerifyRepository.save(emailVerify);

    }

    public void sendMemberMail(String email, String role){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Member] 이메일 인증");
        // uuid 생성
        String uuid = UUID.randomUUID().toString();
        create(email,uuid);
        // jwt 생성
        String jwt = TokenProvider.generateAccessToken(email, role);
        message.setText("http://localhost:8080/memberconfirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        emailSender.send(message);
    }

    public void update(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            Member member = result.get();
            member.setStatus(true);
            memberRepository.save(member);
        }
    }
}

