package com.wedsite.zuong2004.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class ProductRequestUpdate {

    @Size(min = 3, max = 200, message = "NAME_INVALID")
    String name;

    @Min(value = 0, message = "PRICE_MIN")
    @Max(value = 10000000, message = "PRICE_MAX")
    Float price;

    String description;

    String thumbnail;

    @JsonProperty(value = "category_id")
    Long categoryId;
}