package com.woof.api.member.controller;

import com.woof.api.member.model.Ceo;
import com.woof.api.member.model.Member;
import com.woof.api.member.model.requestdto.*;
import com.woof.api.member.model.responsedto.PostCeoSignupRes;
import com.woof.api.member.model.responsedto.PostMemberLoginRes;
import com.woof.api.member.model.responsedto.PostMemberSignupRes;
import com.woof.api.member.service.CeoService;
import com.woof.api.member.service.EmailVerifyService;
import com.woof.api.member.service.MemberService;
import com.woof.api.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private final AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.POST, value = "/member/signup")
    public ResponseEntity signup (@RequestBody PostMemberSignupReq postMemberSignupReq){
        PostMemberSignupRes response = memberService.signup(postMemberSignupReq);
        emailVerifyService.sendMail(postMemberSignupReq.getEmail(), "ROLE_MEMBER");
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/member/login")
    public ResponseEntity login(@RequestBody PostMemberLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.getPrincipal() != null) {
            Member member = (Member)authentication.getPrincipal();
            return ResponseEntity.ok().body(PostMemberLoginRes.builder().accessToken(TokenProvider.generateAccessToken(member.getUsername(), "ROLE_MEMBER")).build());

        }

        return ResponseEntity.badRequest().body("에러");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ceo/signup")
    public ResponseEntity signup (@RequestBody PostCeoSignupReq postCeoSignupReq){
        PostCeoSignupRes response = ceoService.signup(postCeoSignupReq);
        emailVerifyService.sendMail(postCeoSignupReq.getEmail(), "ROLE_CEO");
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ceo/login")
    public ResponseEntity login(@RequestBody PostCeoLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("ceo_"+request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.getPrincipal() != null) {
            Ceo ceo = (Ceo)authentication.getPrincipal();
            return ResponseEntity.ok().body(PostMemberLoginRes.builder().accessToken(TokenProvider.generateAccessToken(ceo.getUsername(), "ROLE_CEO")).build());

        }

        return ResponseEntity.badRequest().body("에러");
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



    @RequestMapping(method = RequestMethod.GET, value = "/test/ceo")
    public String testCeo() {
        return "성공";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/member")
    public String testMember() {
        return "성공";
    }







}
