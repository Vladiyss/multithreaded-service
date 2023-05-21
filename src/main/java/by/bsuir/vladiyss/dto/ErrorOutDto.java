package by.bsuir.vladiyss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorOutDto {

    private final String message;
    private final LocalDateTime timestamp;
}
