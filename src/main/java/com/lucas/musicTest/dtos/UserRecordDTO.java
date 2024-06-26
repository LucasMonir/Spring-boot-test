package com.lucas.musicTest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDTO(@NotBlank String login, @NotBlank String password, @NotBlank String role) {
}
