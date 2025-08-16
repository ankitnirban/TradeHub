package com.example.tradehub.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public record User(
        @Id
        String userName,
        String name,
        String address
) {}
