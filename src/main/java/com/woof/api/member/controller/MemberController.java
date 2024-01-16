package com.woof.api.member.controller;

import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostCeoSignupReq;
import com.woof.api.member.model.requestdto.PostMemberSignupReq;
import com.woof.api.member.model.responsedto.PostCeoSignupRes;
import com.woof.api.member.model.responsedto.PostMemberSignupRes;
import com.woof.api.member.service.CeoService;
import com.woof.api.member.service.EmailVerifyService;
import com.woof.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;
    private final CeoService ceoService;

    @RequestMapping(method = RequestMethod.POST, value = "/member/signup")
    public ResponseEntity signup (@RequestBody PostMemberSignupReq postMemberSignupReq){
        PostMemberSignupRes response = memberService.signup(postMemberSignupReq);
        emailVerifyService.sendMail(postMemberSignupReq.getEmail());
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ceo/signup")
    public ResponseEntity signup (@RequestBody PostCeoSignupReq postCeoSignupReq){
        PostCeoSignupRes response = ceoService.signup(postCeoSignupReq);
        emailVerifyService.sendMail(postCeoSignupReq.getEmail());
        return ResponseEntity.ok().body(response);
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
