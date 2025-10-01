package com.luender.order.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
