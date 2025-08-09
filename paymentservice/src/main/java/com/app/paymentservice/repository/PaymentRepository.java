package com.app.paymentservice.repository;

import com.app.paymentservice.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentOrder,Long> {

    PaymentOrder findByPaymentLinkId(String paymentLinkId);

}
