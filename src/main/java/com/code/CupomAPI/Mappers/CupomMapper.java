package com.code.CupomAPI.Mappers;

import com.code.CupomAPI.DTOs.CupomRequest;
import com.code.CupomAPI.DTOs.CupomResponse;
import com.code.CupomAPI.Entities.Cupom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CupomMapper {
    CupomResponse toResponse(Cupom cupom);
    Cupom toEntity(CupomRequest cupomRequest);
}
