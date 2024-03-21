package com.lucas.musicTest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SongRecordDTO(@NotBlank String name, @NotNull int length) {

}
