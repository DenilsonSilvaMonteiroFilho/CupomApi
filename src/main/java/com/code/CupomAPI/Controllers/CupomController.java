package com.code.CupomAPI.Controllers;

import com.code.CupomAPI.DTOs.CupomRequest;
import com.code.CupomAPI.DTOs.CupomResponse;
import com.code.CupomAPI.Services.CupomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CupomController {

    @Autowired
    CupomService cupomService;

    @PostMapping
    public ResponseEntity<CupomResponse> createCupom(@RequestBody CupomRequest cupomRequest) {
        return cupomService.createCupom(cupomRequest);
    }

    @GetMapping("{id}")
    public ResponseEntity<CupomResponse> getCupomById(@PathVariable UUID id) {
        return cupomService.getCupomById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCupom(@PathVariable UUID id) {
        cupomService.deleteCupom(id);
        return ResponseEntity.noContent().build();
    }
}
