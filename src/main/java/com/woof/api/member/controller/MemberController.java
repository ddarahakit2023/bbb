package com.woof.api.member.controller;

import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostMemberReq;
import com.woof.api.member.model.responsedto.PostMemberRes;
import com.woof.api.member.service.MemberService;
import com.woof.api.member.service.EmailVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    // client에게 요청한 정보 받아오기
    public ResponseEntity signup (@RequestBody PostMemberReq postMemberReq){
//        if(!memberService.getCheckEmail(getEmailConfirmReq)){
//            return null;
//        } else {

        PostMemberRes response = memberService.signup(postMemberReq);

        // 이메일 송신
        emailVerifyService.sendMail(postMemberReq.getEmail());



        return ResponseEntity.ok().body(response);
//        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/confirm")
    public RedirectView confirm(GetEmailConfirmReq getEmailConfirmReq) {

        if (emailVerifyService.confirm(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getUuid())) {
            emailVerifyService.update(getEmailConfirmReq.getEmail());

            return new RedirectView("http://localhost:3000/emailconfirm/" + getEmailConfirmReq.getJwt());
        } else {

            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }







}
