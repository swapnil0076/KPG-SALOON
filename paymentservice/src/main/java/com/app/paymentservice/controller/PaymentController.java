package com.app.paymentservice.controller;

import com.app.paymentservice.domain.PaymentMethod;
import com.app.paymentservice.model.PaymentOrder;
import com.app.paymentservice.payload.response.PaymentLinkResponse;
import com.app.paymentservice.payload.response.payloadDTO.BookingDTO;
import com.app.paymentservice.payload.response.payloadDTO.UserDTO;
import com.app.paymentservice.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException {
        UserDTO user = new UserDTO();
        user.setFullName("Jeevan");
        user.setEmail("jeevan@gmail.com");
        user.setId(1L);

        PaymentLinkResponse response = paymentService.createOrder(user,booking,paymentMethod);

        return ResponseEntity.ok(response);

    }


    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentByOrderId(
          @PathVariable Long paymentOrderId) throws StripeException {
        PaymentOrder response = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPaymentOrder(
            @RequestParam String paymentId, @RequestParam String paymentLinkId) throws RazorpayException {
        PaymentOrder order =  paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean response = paymentService.proceedPayment(order,
                paymentId,
                paymentLinkId);
        return ResponseEntity.ok(response);
    }


}
