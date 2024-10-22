package com.wedsite.zuong2004.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @JsonProperty("fullname")
    String fullName;

    @Size(min = 10, max = 10, message = "PHONE_NUMBER_INVALID")
    @JsonProperty("phone_number")
    String phoneNumber;

    @NotBlank(message = "ADDRESS_INVALID")
    @JsonProperty("address")
    String address;

    @JsonProperty("password")
    @Size(min = 8, max = 32, message = "PASSWORD_INVALID")
    String password;

    @JsonProperty("is_active")
    boolean active;

    @JsonProperty("date_of_birth")
    Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    int facebookAccountId;

    @JsonProperty("google_account_id")
    int googleAccountId;

    @NotNull(message = "ROLE_ID_REQUIRED")
    @JsonProperty("role_id")
    Long roleId;
}
