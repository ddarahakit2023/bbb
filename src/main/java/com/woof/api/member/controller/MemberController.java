package com.woof.api.member.controller;

import com.woof.api.member.model.requestdto.GetEmailConfirmReq;
import com.woof.api.member.model.requestdto.PostManagerSignupReq;
import com.woof.api.member.model.requestdto.PostMemberSignupReq;
import com.woof.api.member.model.responsedto.PostManagerSignupRes;
import com.woof.api.member.model.responsedto.PostMemberSignupRes;
import com.woof.api.member.service.ManagerService;
import com.woof.api.member.service.ManagerEmailVerifyService;
import com.woof.api.member.service.MemberEmailVerifyService;
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

    private final ManagerService managerService;
    private final MemberService memberService;
    private final ManagerEmailVerifyService managerEmailVerifyService;
    private final MemberEmailVerifyService memberEmailVerifyService;

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
}
