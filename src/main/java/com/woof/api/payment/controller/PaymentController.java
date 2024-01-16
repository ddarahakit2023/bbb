package com.woof.api.payment.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.woof.api.common.Response;
import com.woof.api.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {
    private final PaymentService paymentService;
    @RequestMapping(method = RequestMethod.GET, value = "/validation/subscribe")
    public Response validationSubscribe(String impUid) throws IamportResponseException, IOException {
        if(paymentService.subscribeValidation(impUid)) {
            return Response.success("결제 성공");
        }

        return Response.error("결제 금액 이상");
    }

}
