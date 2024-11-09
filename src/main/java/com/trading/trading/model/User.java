package com.trading.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.trading.domain.USER_ROLE;
import com.trading.trading.model.TwoFactorAuth;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "app_user") // Use a different name instead of "user"
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    private USER_ROLE roles = USER_ROLE.ROLE_CUSTOMER;
}