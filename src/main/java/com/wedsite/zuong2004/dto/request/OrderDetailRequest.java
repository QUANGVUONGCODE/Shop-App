package com.wedsite.zuong2004.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {

    @JsonProperty(value = "order_id")
    Long orderId;

    @JsonProperty(value = "produc_id")
    Long productId;

    @JsonProperty(value = "price")
    Float price;

    @JsonProperty(value = "number_of_products")
    int numberOfProduct;

    @JsonProperty(value = "total_money")
    Float totalMoney;

    @JsonProperty(value = "color")
    String color;

}
