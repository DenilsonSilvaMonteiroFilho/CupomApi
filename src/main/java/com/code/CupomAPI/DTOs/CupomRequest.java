package com.code.CupomAPI.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CupomRequest(
        String code,
        String description,
        BigDecimal discountValue,
        LocalDateTime expirationDate,
        boolean published
) {
}
