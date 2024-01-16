package com.woof.api.member.service;

import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.repository.CeoRepository;
import com.woof.api.utils.TokenProvider;
import com.woof.api.member.model.entity.EmailVerify;
import com.woof.api.member.repository.CeoEmailVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CeoEmailVerifyService {

    private final CeoEmailVerifyRepository ceoEmailVerifyRepository;
    private final TokenProvider tokenProvider;
    private final JavaMailSender emailSender;
    private final CeoRepository ceoRepository;

    public Boolean confirm(String email, String uuid){
        Optional<EmailVerify> result = ceoEmailVerifyRepository.findByEmail(email);

        if(result.isPresent()) {
            EmailVerify emailVerify = result.get();

            if(emailVerify.getUuid().equals(uuid)) {
                Optional<Ceo> ceo = ceoRepository.findByEmail(email);
                if (ceo.isPresent()) {
                    ceo.get().setStatus(true);
                    ceoRepository.save(ceo.get());
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

        ceoEmailVerifyRepository.save(emailVerify);

    }

    public void sendCeoMail (String email, String role){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[CEO] 이메일 인증");
        // uuid 생성
        String uuid = UUID.randomUUID().toString();
        create(email,uuid);
        // jwt 생성
        String jwt = TokenProvider.generateAccessToken(email,role);
        message.setText("http://localhost:8080/ceoconfirm?email=" + email + "&uuid=" + uuid + "&jwt=" + jwt);
        emailSender.send(message);
    }

    public void update(String email) {
        Optional<Ceo> result = ceoRepository.findByEmail(email);
        if (result.isPresent()) {
            Ceo ceo = result.get();
            ceo.setStatus(true);
            ceoRepository.save(ceo);
        }
    }
}


