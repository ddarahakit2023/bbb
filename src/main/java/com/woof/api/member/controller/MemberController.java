package com.woof.api.member.controller;

import com.woof.api.member.model.entity.Ceo;
import com.woof.api.member.model.entity.Manager;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.requestdto.*;
import com.woof.api.member.model.responsedto.*;
import com.woof.api.member.service.*;
import com.woof.api.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.ColumnResult;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final ManagerService managerService;
    private final MemberService memberService;
    private final CeoService ceoService;
    private final ManagerEmailVerifyService managerEmailVerifyService;
    private final MemberEmailVerifyService memberEmailVerifyService;
    private final AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.POST, value = "/member/signup")
    public ResponseEntity signup (@RequestBody PostMemberSignupReq postMemberSignupReq){
        PostMemberSignupRes response = memberService.signup(postMemberSignupReq);
        memberEmailVerifyService.sendMemberMail(postMemberSignupReq.getEmail(), "ROLE_MEMBER");
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/manager/signup")
    public ResponseEntity signup (@RequestBody PostManagerSignupReq postManagerSignupReq){
        PostManagerSignupRes response = managerService.signup(postManagerSignupReq);
        managerEmailVerifyService.sendManagerMail(postManagerSignupReq.getEmail(), "ROLE_MANAGER");
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ceo/signup")
    public ResponseEntity signup (@RequestBody PostCeoSignupReq postCeoSignupReq){
        PostCeoSignupRes response = ceoService.signup(postCeoSignupReq);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/memberconfirm")
    public RedirectView memberConfirm(GetEmailConfirmReq getEmailConfirmReq) {

        if (memberEmailVerifyService.confirm(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getUuid())) {
            memberEmailVerifyService.update(getEmailConfirmReq.getEmail());

            return new RedirectView("http://localhost:3000/emailconfirm/" + getEmailConfirmReq.getJwt());
        } else {

            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/managerconfirm")
    public RedirectView managerConfirm(GetEmailConfirmReq getEmailConfirmReq) {

        if (managerEmailVerifyService.confirm(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getUuid())) {
            managerEmailVerifyService.update(getEmailConfirmReq.getEmail());

            return new RedirectView("http://localhost:3000/emailconfirm/" + getEmailConfirmReq.getJwt());
        } else {

            return new RedirectView("http://localhost:3000/emailCertError");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/member/login")
    public ResponseEntity login(@RequestBody PostMemberLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.getPrincipal() != null) {
            Member member = (Member)authentication.getPrincipal();
            return ResponseEntity.ok().body(PostMemberLoginRes.builder().accessToken(TokenProvider.generateAccessToken(member.getUsername(), "ROLE_MEMBER")).idx(member.getIdx()).build());

        }

        return ResponseEntity.badRequest().body("에러");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/manager/login")
    public ResponseEntity login(@RequestBody PostManagerLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("manager_"+request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.getPrincipal() != null) {
            Manager manager = (Manager) authentication.getPrincipal();
            return ResponseEntity.ok().body(PostManagerLoginRes.builder().accessToken(TokenProvider.generateAccessToken(manager.getUsername(), "ROLE_MANAGER")).build());
        }
        return ResponseEntity.badRequest().body("에러");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ceo/login")
    public ResponseEntity login(@RequestBody PostCeoLoginReq request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("ceo_"+request.getBusinessnum(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authentication.getPrincipal() != null) {
            Ceo ceo = (Ceo) authentication.getPrincipal();
            return ResponseEntity.ok().body(PostManagerLoginRes.builder().accessToken(TokenProvider.generateAccessToken(ceo.getUsername(), "ROLE_CEO")).build());
        }
        return ResponseEntity.badRequest().body("에러");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/manager")
    public String testManager() {
        return "성공";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/member")
    public String testMember() {
        return "성공";
    }

    @GetMapping("/member/{email}")
    public ResponseEntity read(@PathVariable String email) {
        GetMemberReadRes response = memberService.readMember(email);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/member/update")
    public ResponseEntity update (@RequestBody PatchMemberUpdateReq request) {
        PatchMemberUpdateRes response = memberService.updateMember(request);
        return ResponseEntity.ok().body(response);
    }


}
