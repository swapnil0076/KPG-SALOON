package com.app.paymentservice.service;

import com.app.paymentservice.domain.PaymentMethod;
import com.app.paymentservice.domain.PaymentOrderStatus;
import com.app.paymentservice.model.PaymentOrder;
import com.app.paymentservice.payload.response.PaymentLinkResponse;
import com.app.paymentservice.payload.response.payloadDTO.BookingDTO;
import com.app.paymentservice.payload.response.payloadDTO.UserDTO;
import com.app.paymentservice.repository.PaymentRepository;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayAPIKey;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws StripeException {

        Long amount = (long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentRepository.save(order);

        PaymentLinkResponse response = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink paymentLink = createRazorPaymentLink(user,savedOrder.getAmount(),savedOrder.getId());

            String paymentURL = paymentLink.get("short_url");
            String paymentID = paymentLink.get("id");
            response.setPayment_link_url(paymentURL);
            response.setPayment_link_id(paymentID);
            savedOrder.setPaymentLinkId(paymentID);

        }else{
            String paymentUrl = createStripePaymentLink(user,savedOrder.getAmount(),savedOrder.getId());
            response.setPayment_link_url(paymentUrl);
        }

        return response;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        PaymentOrder paymentOrder = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment History not found with Id"+id));
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorPaymentLink(UserDTO user,
                                              Long Amount,
                                              Long orderId) {
        Long amount = Amount*100;

        try {
            RazorpayClient razorpay=new RazorpayClient(razorpayAPIKey,razorpaySecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("reminder_enable",true);

            paymentLinkRequest.put("call_back_url","http://localhost:3000/payment-sucess"+orderId);

            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);

            return paymentLink;

        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-sucess "+orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel"+orderId)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder().setName("salon appointment booking").build()
                                ).build()
                        ).build()
                ).build();

        Session session = Session.create(params);


        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLInkId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpay = new RazorpayClient(razorpayAPIKey,razorpaySecret);

                Payment payment = razorpay.payments.fetch(paymentId);
                Integer amount=payment.get("amount");
                String status=payment.get("status");

                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentRepository.save(paymentOrder);
                    return true;
                }

            }
        }else {
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentRepository.save(paymentOrder);
            return true;
        }

        return  false;
    }

}
