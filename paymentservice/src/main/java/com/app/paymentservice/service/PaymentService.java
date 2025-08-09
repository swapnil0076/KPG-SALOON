package com.app.paymentservice.service;

import com.app.paymentservice.domain.PaymentMethod;
import com.app.paymentservice.model.PaymentOrder;
import com.app.paymentservice.payload.response.PaymentLinkResponse;
import com.app.paymentservice.payload.response.payloadDTO.BookingDTO;
import com.app.paymentservice.payload.response.payloadDTO.UserDTO;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws StripeException;
    PaymentOrder getPaymentOrderById(Long id);
    PaymentOrder getPaymentOrderByPaymentId(String paymentId);
    PaymentLink createRazorPaymentLink(UserDTO user,Long amount,Long orderId);
    String createStripePaymentLink(UserDTO user,Long amount,Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLInkId) throws RazorpayException;
}
