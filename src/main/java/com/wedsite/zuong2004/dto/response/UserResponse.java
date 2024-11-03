package com.wedsite.zuong2004.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;

    @JsonProperty(value = "fullname")
    String fullName;

    @JsonProperty(value = "phone_number")
    String phoneNumber;

    String address;
    String password;

    @JsonProperty(value = "date_of_birth")
    Date dateOfBirth;

    @JsonProperty(value = "facebook_account_id")
    int facebookAccountId;

    @JsonProperty(value = "google_account_id")
    int googleAccountId;

    @JsonProperty(value = "is_active")
    boolean active;

    @JsonProperty(value = "role_id")
    Long roleId;

    @JsonProperty(value = "created_at")
    LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
