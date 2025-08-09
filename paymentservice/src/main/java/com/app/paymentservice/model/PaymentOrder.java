package com.app.paymentservice.model;

import com.app.paymentservice.domain.PaymentMethod;
import com.app.paymentservice.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long amount;
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;
    @Column(nullable = false)
    private PaymentMethod paymentMethod;
    private String paymentLinkId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long bookingId;
    @Column(nullable = false)
    private Long salonId;
}
