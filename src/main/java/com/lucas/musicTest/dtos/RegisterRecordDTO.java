package com.lucas.musicTest.dtos;

import com.lucas.musicTest.models.user.UserRole;

public record RegisterRecordDTO(String login, String password, UserRole role) {
}
