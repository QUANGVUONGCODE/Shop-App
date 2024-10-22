package com.wedsite.zuong2004.enity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import java.time.LocalDate;

@Table(name = "orders")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "fullname", nullable = false, length = 100)
    String fullName;

    @Column(name = "email", length = 100)
    String email;

    @Column(name = "phone_number", length = 10)
    String phoneNumber;

    @Column(name = "address", length = 200)
    String address;

    @Column(name = "note", length = 200)
    String note;

    @Column(name = "order_date")
    Date orderDate;

    @Column(name = "status", length = 255)
    String status;

    @Column(name = "total_money")
    Float totalMoney;

    @Column(name = "shipping_method")
    String shippingMethod;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "shipping_date")
    LocalDate shippingDate;

    @Column(name = "is_active")
    boolean active;
}
