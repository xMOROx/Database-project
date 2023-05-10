package org.agh.edu.pl.carrentalrestapi.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorDetails(Map<String, String> messages, String details, LocalDateTime timestamp, Integer status) {
}
