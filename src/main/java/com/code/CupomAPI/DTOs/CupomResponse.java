package com.code.CupomAPI.DTOs;

import com.code.CupomAPI.Entities.Enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CupomResponse(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDateTime expirationDate,
        Status status,
        boolean published,
        boolean redeemed
) {
}
