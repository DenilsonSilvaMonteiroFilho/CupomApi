package com.code.CupomAPI;

import com.code.CupomAPI.Services.CupomService;
import com.code.CupomAPI.DTOs.CupomRequest;
import com.code.CupomAPI.DTOs.CupomResponse;
import com.code.CupomAPI.Entities.Cupom;
import com.code.CupomAPI.Entities.Enums.Status;
import com.code.CupomAPI.Mappers.CupomMapper;
import com.code.CupomAPI.Repositories.CupomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CupomServiceTest {

    @Mock
    private CupomRepository cupomRepository;

    @Mock
    private CupomMapper mapper;

    @InjectMocks
    private CupomService cupomService;

    private Cupom cupom;
    private CupomRequest cupomRequest;
    private CupomResponse cupomResponse;

    @BeforeEach
    void setup() {
        cupom = new Cupom();
        cupom.setCode("ABC123");
        cupom.setDescription("Cupom de teste");
        cupom.setDiscountValue(BigDecimal.valueOf(10));
        cupom.setExpirationDate(LocalDateTime.now().plusDays(10));
        cupom.setStatus(Status.ACTIVE);

        cupomRequest = new CupomRequest(
                "ABC-123",
                "Cupom de teste",
                BigDecimal.valueOf(10),
                LocalDateTime.now().plusDays(10),
                true
        );

        cupomResponse = new CupomResponse(
                UUID.randomUUID(),
                "ABC123",
                "Cupom de teste",
                BigDecimal.valueOf(10),
                cupom.getExpirationDate(),
                Status.ACTIVE,
                true,
                false
        );
    }

    // =========================
    // CREATE CUPOM
    // =========================

    @Test
    void deveCriarCupomComSucesso() {
        when(mapper.toEntity(cupomRequest)).thenReturn(cupom);
        when(cupomRepository.save(any(Cupom.class))).thenReturn(cupom);
        when(mapper.toResponse(cupom)).thenReturn(cupomResponse);

        ResponseEntity<CupomResponse> response = cupomService.createCupom(cupomRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ABC123", response.getBody().code());

        verify(cupomRepository).save(cupom);
    }

    @Test
    void deveLancarExcecaoQuandoCodigoNaoTem6Caracteres() {
        cupom.setCode("AB1");

        when(mapper.toEntity(cupomRequest)).thenReturn(cupom);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cupomService.createCupom(cupomRequest)
        );

        assertEquals("O código do cupom deve conter exatamente 6 caracteres.", exception.getMessage());
        verify(cupomRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoDescontoMenorQueMinimo() {
        cupom.setDiscountValue(BigDecimal.valueOf(0.1));

        when(mapper.toEntity(cupomRequest)).thenReturn(cupom);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cupomService.createCupom(cupomRequest)
        );

        assertEquals("O valor de desconto do cupom deve ser no mínimo 0.5.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDataExpiracaoNoPassado() {
        cupom.setExpirationDate(LocalDateTime.now().minusDays(1));

        when(mapper.toEntity(cupomRequest)).thenReturn(cupom);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cupomService.createCupom(cupomRequest)
        );

        assertEquals("A data de expiração do cupom não pode estar no passado.", exception.getMessage());
    }

    // =========================
    // GET ALL CUPONS
    // =========================

    @Test
    void deveRetornarListaDeCupoms() {
        when(cupomRepository.findAll()).thenReturn(List.of(cupom));
        when(mapper.toResponse(cupom)).thenReturn(cupomResponse);

        ResponseEntity<List<CupomResponse>> response = cupomService.getAllCupoms();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    // =========================
    // GET CUPOM BY ID
    // =========================

    @Test
    void deveBuscarCupomPorIdComSucesso() {
        UUID id = UUID.randomUUID();

        when(cupomRepository.findById(id)).thenReturn(Optional.of(cupom));
        when(mapper.toResponse(cupom)).thenReturn(cupomResponse);

        ResponseEntity<CupomResponse> response = cupomService.getCupomById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveLancarExcecaoQuandoCupomNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(cupomRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cupomService.getCupomById(id)
        );

        assertTrue(exception.getMessage().contains("Cupom não encontrado"));
    }

    // =========================
    // DELETE CUPOM
    // =========================

    @Test
    void deveDeletarCupomComSucesso() {
        UUID id = UUID.randomUUID();
        cupom.setStatus(Status.ACTIVE);

        when(cupomRepository.findById(id)).thenReturn(Optional.of(cupom));
        when(cupomRepository.save(any())).thenReturn(cupom);

        ResponseEntity<Void> response = cupomService.deleteCupom(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.DELETED, cupom.getStatus());
    }

    @Test
    void deveLancarExcecaoAoDeletarCupomJaDeletado() {
        UUID id = UUID.randomUUID();
        cupom.setStatus(Status.DELETED);

        when(cupomRepository.findById(id)).thenReturn(Optional.of(cupom));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cupomService.deleteCupom(id)
        );

        assertEquals("Cupom já está deletado.", exception.getMessage());
        verify(cupomRepository, never()).save(any());
    }

    @Test
    void deveChamarSaveAoDeletarCupom() {
        UUID id = UUID.randomUUID();
        cupom.setStatus(Status.ACTIVE);

        when(cupomRepository.findById(id)).thenReturn(Optional.of(cupom));
        when(cupomRepository.save(any(Cupom.class))).thenReturn(cupom);

        cupomService.deleteCupom(id);

        verify(cupomRepository, times(1)).save(cupom);
    }

}
