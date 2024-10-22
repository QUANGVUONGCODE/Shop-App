package com.wedsite.zuong2004.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.util.Date;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;

    @JsonProperty(value = "user_id")
    Long userId;

    @JsonProperty(value = "fullname")
    String fullName;

    @JsonProperty(value = "email")
    String email;

    @JsonProperty(value = "phone_number")
    String phoneNumber;

    @JsonProperty(value = "address")
    String address;

    @JsonProperty(value = "note")
    String note;

    @JsonProperty(value = "order_date")
    Date orderDate;

    @JsonProperty(value = "status")
    String status;

    @JsonProperty(value = "total_money")
    Float totalMoney;

    @JsonProperty(value = "shipping_method")
    String shippingMethod;

    @JsonProperty(value = "payment_method")
    String paymentMethod;

    @JsonProperty(value = "shipping_date")
    LocalDate shippingDate;

    @JsonProperty(value = "is_active")
    boolean active;
}
