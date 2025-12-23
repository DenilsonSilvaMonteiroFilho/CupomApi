package com.code.CupomAPI.Services;

import com.code.CupomAPI.DTOs.CupomRequest;
import com.code.CupomAPI.DTOs.CupomResponse;
import com.code.CupomAPI.Entities.Cupom;
import com.code.CupomAPI.Entities.Enums.Status;
import com.code.CupomAPI.Mappers.CupomMapper;
import com.code.CupomAPI.Repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CupomService {

    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    CupomMapper mapper;

    public ResponseEntity<CupomResponse> createCupom(CupomRequest cupomRequest) {
        Cupom cupom = mapper.toEntity(cupomRequest);
        validarDadosCupom(cupom);
        cupom.setStatus(Status.ACTIVE);
        return new ResponseEntity(mapper.toResponse(cupomRepository.save(cupom)), HttpStatus.CREATED);
    }

    public ResponseEntity<List<CupomResponse>> getAllCupoms() {
        List<Cupom> cupoms = cupomRepository.findAll();
        List<CupomResponse> responses = cupoms.stream()
                .map(mapper::toResponse)
                .toList();
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    public ResponseEntity<CupomResponse> getCupomById(UUID id) {
        Cupom cupom = this.validarCupom(id);
        return new ResponseEntity(mapper.toResponse(cupom), HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteCupom(UUID id) {
        Cupom cupom = this.validarCupom(id);
        if (cupom.getStatus().equals(Status.DELETED)){
            throw new IllegalArgumentException("Cupom já está deletado.");
        } else {
            this.delete(cupom);
            return ResponseEntity.ok().build();
        }

    }

    private void delete(Cupom cupom) {
        cupom.setStatus(Status.DELETED);
        cupomRepository.save(cupom);
    }

    private void validarDadosCupom(Cupom cupom) {
        String sanitizedCode = cupom.getCode().replaceAll("[^a-zA-Z0-9]", "");

        if (sanitizedCode.length() != 6) {
            throw new IllegalArgumentException("O código do cupom deve conter exatamente 6 caracteres.");
        }
        cupom.setCode(sanitizedCode);

        // Validar se o valor de desconto é maior ou igual a 0.5
        if (cupom.getDiscountValue().compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new IllegalArgumentException("O valor de desconto do cupom deve ser no mínimo 0.5.");
        }

        // Validar se a data de expiração não está no passado
        if (cupom.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de expiração do cupom não pode estar no passado.");
        }
    }

    private Cupom validarCupom(UUID id) {
        return cupomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado id: " + id));
    }
}
